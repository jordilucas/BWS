package br.com.bwsmobile.bwsmobile.game;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.bwsmobile.bwsmobile.R;

public class DesenhoPuzzleActivity extends AppCompatActivity implements
        PuzzleGridView.GridListener,
        PuzzlePaletteView.PaletteListener {

    private static final String PREFS_NAME = "desenho_puzzle_prefs";
    private static final String KEY_LEVEL = "current_level";
    private static final String KEY_SCORE = "score";

    private PuzzleLevelRepository repository;
    private int currentLevelIndex;
    private int score;

    private PuzzleGridView gridView;
    private PuzzlePaletteView paletteView;
    private View floatingPiece;
    private TextView levelTitle;
    private TextView scoreText;
    private TextView difficultyText;
    private TextView instructionText;
    private Button btnReset;
    private Button btnHint;
    private View celebrationOverlay;

    private int selectedColor = PuzzleLevel.COLOR_EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desenho_puzzle);

        repository = new PuzzleLevelRepository();

        gridView = (PuzzleGridView) findViewById(R.id.puzzleGrid);
        paletteView = (PuzzlePaletteView) findViewById(R.id.puzzlePalette);
        floatingPiece = findViewById(R.id.floatingPiece);
        levelTitle = (TextView) findViewById(R.id.levelTitle);
        scoreText = (TextView) findViewById(R.id.scoreText);
        difficultyText = (TextView) findViewById(R.id.difficultyText);
        instructionText = (TextView) findViewById(R.id.instructionText);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnHint = (Button) findViewById(R.id.btnHint);
        celebrationOverlay = findViewById(R.id.celebrationOverlay);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        score = prefs.getInt(KEY_SCORE, 0);
        currentLevelIndex = prefs.getInt(KEY_LEVEL, 0);
        scoreText.setText(getString(R.string.puzzle_score, score));

        gridView.setListener(this);
        paletteView.setListener(this);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLevel(currentLevelIndex);
            }
        });

        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DesenhoPuzzleActivity.this,
                        R.string.puzzle_hint_message, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnNextLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celebrationOverlay.setVisibility(View.GONE);
                if (currentLevelIndex < repository.getLevelCount() - 1) {
                    currentLevelIndex++;
                    saveProgress();
                    loadLevel(currentLevelIndex);
                } else {
                    Toast.makeText(DesenhoPuzzleActivity.this,
                            R.string.puzzle_all_complete, Toast.LENGTH_LONG).show();
                    currentLevelIndex = 0;
                    saveProgress();
                    loadLevel(currentLevelIndex);
                }
            }
        });

        loadLevel(currentLevelIndex);
    }

    private void loadLevel(int index) {
        PuzzleLevel level = repository.getLevel(index);
        currentLevelIndex = index;
        selectedColor = PuzzleLevel.COLOR_EMPTY;
        floatingPiece.setVisibility(View.GONE);

        gridView.setLevel(level);
        paletteView.setupForLevel(level);

        levelTitle.setText(getString(R.string.puzzle_level_title,
                level.getNumber(), level.getEmoji(), level.getName()));
        difficultyText.setText(getString(R.string.puzzle_difficulty, level.getDifficulty()));

        if (level.isShowHint()) {
            instructionText.setText(R.string.puzzle_instruction_easy);
            btnHint.setVisibility(View.VISIBLE);
        } else {
            instructionText.setText(R.string.puzzle_instruction_hard);
            btnHint.setVisibility(View.GONE);
        }

        celebrationOverlay.setVisibility(View.GONE);
    }

    @Override
    public void onCellTapped(int row, int col) {
        if (selectedColor != PuzzleLevel.COLOR_EMPTY && !gridView.isCellLocked(row, col)) {
            tryPlacePiece(row, col, selectedColor);
        } else if (!gridView.isCellLocked(row, col)
                && gridView.getTargetColorAt(row, col) != PuzzleLevel.COLOR_EMPTY) {
            gridView.removePlacement(row, col);
        }
    }

    @Override
    public void onCellPlaced(int row, int col, int colorId, boolean correct) {
        if (correct) {
            animateSuccess();
            checkWin();
        } else {
            Toast.makeText(this, R.string.puzzle_wrong_color, Toast.LENGTH_SHORT).show();
            gridView.removePlacement(row, col);
            paletteView.returnPiece(colorId);
        }
    }

    @Override
    public void onPiecePicked(int colorId) {
        selectedColor = colorId;
        floatingPiece.setVisibility(View.VISIBLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(16f);
        drawable.setColor(PuzzleColorUtils.getColorForId(this, colorId));
        drawable.setStroke(3, ContextCompat.getColor(this, R.color.puzzle_grid_border));
        floatingPiece.setBackground(drawable);
    }

    @Override
    public void onPieceDragMove(int colorId, float rawX, float rawY) {
        floatingPiece.setVisibility(View.VISIBLE);
        floatingPiece.setX(rawX - floatingPiece.getWidth() / 2f - getWindowX());
        floatingPiece.setY(rawY - floatingPiece.getHeight() / 2f - getWindowY());
    }

    @Override
    public void onPieceDropped(int colorId, float rawX, float rawY) {
        int[] location = new int[2];
        gridView.getLocationOnScreen(location);

        float localX = rawX - location[0];
        float localY = rawY - location[1];
        int[] cell = gridView.getCellAt(localX, localY);

        if (cell != null) {
            tryPlacePiece(cell[0], cell[1], colorId);
        }

        selectedColor = PuzzleLevel.COLOR_EMPTY;
        floatingPiece.setVisibility(View.GONE);
    }

    @Override
    public void onPieceReleased() {
        selectedColor = PuzzleLevel.COLOR_EMPTY;
        floatingPiece.setVisibility(View.GONE);
    }

    private void tryPlacePiece(int row, int col, int colorId) {
        if (gridView.getTargetColorAt(row, col) == PuzzleLevel.COLOR_EMPTY) {
            return;
        }

        if (paletteView.usePiece(colorId)) {
            boolean correct = gridView.placeColor(row, col, colorId);
            if (!correct) {
                paletteView.returnPiece(colorId);
            }
        }
    }

    private void checkWin() {
        if (gridView.isComplete()) {
            PuzzleLevel level = repository.getLevel(currentLevelIndex);
            int bonus = level.getDifficulty() * 10;
            score += 50 + bonus;
            scoreText.setText(getString(R.string.puzzle_score, score));
            saveProgress();

            TextView celebrationText = (TextView) findViewById(R.id.celebrationText);
            celebrationText.setText(getString(R.string.puzzle_level_complete,
                    level.getEmoji(), level.getName()));

            celebrationOverlay.setVisibility(View.VISIBLE);
            animateCelebration(celebrationOverlay);
        }
    }

    private void animateSuccess() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(gridView, "scaleX", 1f, 1.03f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(gridView, "scaleY", 1f, 1.03f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.setDuration(200);
        set.start();
    }

    private void animateCelebration(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.setDuration(600);
        set.setInterpolator(new BounceInterpolator());
        set.start();
    }

    private float getWindowX() {
        int[] location = new int[2];
        findViewById(android.R.id.content).getLocationOnScreen(location);
        return location[0];
    }

    private float getWindowY() {
        int[] location = new int[2];
        findViewById(android.R.id.content).getLocationOnScreen(location);
        return location[1];
    }

    private void saveProgress() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit()
                .putInt(KEY_LEVEL, currentLevelIndex)
                .putInt(KEY_SCORE, score)
                .apply();
    }
}
