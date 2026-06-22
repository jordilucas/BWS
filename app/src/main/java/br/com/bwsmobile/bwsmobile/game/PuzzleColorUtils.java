package br.com.bwsmobile.bwsmobile.game;

import android.content.Context;
import android.graphics.Color;

import br.com.bwsmobile.bwsmobile.R;

public class PuzzleColorUtils {

    private PuzzleColorUtils() {
    }

    public static int getColorForId(Context context, int colorId) {
        switch (colorId) {
            case 1:
                return context.getResources().getColor(R.color.puzzle_red);
            case 2:
                return context.getResources().getColor(R.color.puzzle_yellow);
            case 3:
                return context.getResources().getColor(R.color.puzzle_blue);
            case 4:
                return context.getResources().getColor(R.color.puzzle_green);
            case 5:
                return context.getResources().getColor(R.color.puzzle_orange);
            case 6:
                return context.getResources().getColor(R.color.puzzle_purple);
            case 7:
                return context.getResources().getColor(R.color.puzzle_pink);
            default:
                return Color.TRANSPARENT;
        }
    }

    public static int getHintColorForId(Context context, int colorId) {
        int base = getColorForId(context, colorId);
        return Color.argb(80, Color.red(base), Color.green(base), Color.blue(base));
    }

    public static String getColorName(int colorId) {
        switch (colorId) {
            case 1:
                return "Vermelho";
            case 2:
                return "Amarelo";
            case 3:
                return "Azul";
            case 4:
                return "Verde";
            case 5:
                return "Laranja";
            case 6:
                return "Roxo";
            case 7:
                return "Rosa";
            default:
                return "";
        }
    }
}
