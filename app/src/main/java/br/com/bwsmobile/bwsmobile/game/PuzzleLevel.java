package br.com.bwsmobile.bwsmobile.game;

public class PuzzleLevel {

    public static final int GRID_SIZE = 6;
    public static final int COLOR_EMPTY = 0;

    private final int number;
    private final String name;
    private final String emoji;
    private final int[][] target;
    private final boolean showHint;
    private final int difficulty;

    public PuzzleLevel(int number, String name, String emoji, int[][] target,
                       boolean showHint, int difficulty) {
        this.number = number;
        this.name = name;
        this.emoji = emoji;
        this.target = target;
        this.showHint = showHint;
        this.difficulty = difficulty;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public int[][] getTarget() {
        return target;
    }

    public boolean isShowHint() {
        return showHint;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int countCellsForColor(int color) {
        int count = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (target[row][col] == color) {
                    count++;
                }
            }
        }
        return count;
    }
}
