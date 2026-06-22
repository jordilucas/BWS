package br.com.bwsmobile.bwsmobile.game;

import java.util.ArrayList;
import java.util.List;

public class PuzzleLevelRepository {

    private static final int R = 1; // vermelho
    private static final int Y = 2; // amarelo
    private static final int B = 3; // azul
    private static final int G = 4; // verde
    private static final int O = 5; // laranja
    private static final int P = 6; // roxo
    private static final int K = 7; // rosa

    private static final int E = PuzzleLevel.COLOR_EMPTY;

    private final List<PuzzleLevel> levels;

    public PuzzleLevelRepository() {
        levels = new ArrayList<PuzzleLevel>();
        buildLevels();
    }

    public List<PuzzleLevel> getLevels() {
        return levels;
    }

    public PuzzleLevel getLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            return levels.get(0);
        }
        return levels.get(index);
    }

    public int getLevelCount() {
        return levels.size();
    }

    private void buildLevels() {
        // Nível 1 - Coração (fácil, com dica)
        levels.add(new PuzzleLevel(1, "Coração", "❤️", new int[][]{
                {E, R, R, R, R, E},
                {R, R, R, R, R, R},
                {R, R, R, R, R, R},
                {E, R, R, R, R, E},
                {E, E, R, R, E, E},
                {E, E, E, E, E, E}
        }, true, 1));

        // Nível 2 - Estrela
        levels.add(new PuzzleLevel(2, "Estrela", "⭐", new int[][]{
                {E, E, Y, Y, E, E},
                {E, Y, Y, Y, Y, E},
                {Y, Y, Y, Y, Y, Y},
                {E, Y, Y, Y, Y, E},
                {E, Y, E, E, Y, E},
                {Y, E, E, E, E, Y}
        }, true, 1));

        // Nível 3 - Sorriso
        levels.add(new PuzzleLevel(3, "Sorriso", "😊", new int[][]{
                {E, Y, Y, Y, Y, E},
                {Y, E, Y, Y, E, Y},
                {Y, Y, Y, Y, Y, Y},
                {Y, E, Y, Y, E, Y},
                {E, B, E, E, B, E},
                {E, B, B, B, B, E}
        }, true, 2));

        // Nível 4 - Casa
        levels.add(new PuzzleLevel(4, "Casa", "🏠", new int[][]{
                {E, E, O, O, E, E},
                {E, O, O, O, O, E},
                {O, O, O, O, O, O},
                {E, R, R, R, R, E},
                {E, R, B, B, R, E},
                {E, R, R, R, R, E}
        }, true, 2));

        // Nível 5 - Flor (sem dica forte)
        levels.add(new PuzzleLevel(5, "Flor", "🌸", new int[][]{
                {E, P, E, E, P, E},
                {P, K, P, P, K, P},
                {E, P, G, G, P, E},
                {E, E, G, G, E, E},
                {E, E, G, G, E, E},
                {E, E, G, G, E, E}
        }, false, 3));

        // Nível 6 - Peixe
        levels.add(new PuzzleLevel(6, "Peixe", "🐟", new int[][]{
                {E, E, E, B, B, E},
                {E, E, B, B, B, B},
                {E, B, B, Y, B, B},
                {B, B, B, B, B, E},
                {E, B, B, B, E, E},
                {E, E, O, E, E, E}
        }, false, 3));

        // Nível 7 - Árvore
        levels.add(new PuzzleLevel(7, "Árvore", "🌳", new int[][]{
                {E, E, G, G, E, E},
                {E, G, G, G, G, E},
                {G, G, G, G, G, G},
                {E, G, G, G, G, E},
                {E, E, O, O, E, E},
                {E, E, O, O, E, E}
        }, false, 4));

        // Nível 8 - Borboleta
        levels.add(new PuzzleLevel(8, "Borboleta", "🦋", new int[][]{
                {P, E, E, E, E, P},
                {P, P, E, E, P, P},
                {P, P, P, P, P, P},
                {E, P, P, P, P, E},
                {E, E, P, P, E, E},
                {E, E, K, K, E, E}
        }, false, 4));

        // Nível 9 - Foguete
        levels.add(new PuzzleLevel(9, "Foguete", "🚀", new int[][]{
                {E, E, R, R, E, E},
                {E, R, R, R, R, E},
                {E, R, B, B, R, E},
                {E, R, R, R, R, E},
                {E, O, E, E, O, E},
                {O, O, E, E, O, O}
        }, false, 5));

        // Nível 10 - Arco-íris (mais difícil)
        levels.add(new PuzzleLevel(10, "Arco-íris", "🌈", new int[][]{
                {R, R, R, R, R, R},
                {O, O, O, O, O, O},
                {Y, Y, Y, Y, Y, Y},
                {G, G, G, G, G, G},
                {B, B, B, B, B, B},
                {P, P, P, P, P, P}
        }, false, 5));
    }
}
