package br.com.bwsmobile.bwsmobile.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import br.com.bwsmobile.bwsmobile.R;

public class PuzzleGridView extends View {

    public interface GridListener {
        void onCellTapped(int row, int col);

        void onCellPlaced(int row, int col, int colorId, boolean correct);
    }

    private static final int GRID_SIZE = PuzzleLevel.GRID_SIZE;

    private PuzzleLevel level;
    private int[][] placed;
    private boolean[][] locked;
    private GridListener listener;

    private final Paint cellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint lockedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float cellSize;
    private float gridOffsetX;
    private float gridOffsetY;

    public PuzzleGridView(Context context) {
        super(context);
        init();
    }

    public PuzzleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PuzzleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f);
        borderPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_grid_border));

        lockedPaint.setStyle(Paint.Style.STROKE);
        lockedPaint.setStrokeWidth(6f);
        lockedPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_locked_border));
    }

    public void setListener(GridListener listener) {
        this.listener = listener;
    }

    public void setLevel(PuzzleLevel level) {
        this.level = level;
        placed = new int[GRID_SIZE][GRID_SIZE];
        locked = new boolean[GRID_SIZE][GRID_SIZE];
        invalidate();
    }

    public void resetPlacements() {
        if (placed != null) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    placed[row][col] = PuzzleLevel.COLOR_EMPTY;
                    locked[row][col] = false;
                }
            }
            invalidate();
        }
    }

    public boolean placeColor(int row, int col, int colorId) {
        if (level == null || !isValidCell(row, col)) {
            return false;
        }

        int targetColor = level.getTarget()[row][col];
        if (targetColor == PuzzleLevel.COLOR_EMPTY) {
            return false;
        }

        if (locked[row][col]) {
            return false;
        }

        placed[row][col] = colorId;
        boolean correct = colorId == targetColor;
        if (correct) {
            locked[row][col] = true;
        }

        invalidate();

        if (listener != null) {
            listener.onCellPlaced(row, col, colorId, correct);
        }

        return correct;
    }

    public void removePlacement(int row, int col) {
        if (level == null || !isValidCell(row, col)) {
            return;
        }

        if (locked[row][col]) {
            return;
        }

        placed[row][col] = PuzzleLevel.COLOR_EMPTY;
        invalidate();
    }

    public boolean isComplete() {
        if (level == null) {
            return false;
        }

        int[][] target = level.getTarget();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (target[row][col] != PuzzleLevel.COLOR_EMPTY) {
                    if (!locked[row][col]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getTargetColorAt(int row, int col) {
        if (level == null || !isValidCell(row, col)) {
            return PuzzleLevel.COLOR_EMPTY;
        }
        return level.getTarget()[row][col];
    }

    public boolean isCellLocked(int row, int col) {
        return locked != null && isValidCell(row, col) && locked[row][col];
    }

    private void updateGridMetrics() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        float gridSize = Math.min(getWidth(), getHeight());
        float padding = gridSize * 0.02f;
        float available = gridSize - padding * 2;
        cellSize = available / GRID_SIZE;
        gridOffsetX = (getWidth() - gridSize) / 2f + padding;
        gridOffsetY = (getHeight() - gridSize) / 2f + padding;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateGridMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (level == null) {
            return;
        }

        updateGridMetrics();

        int[][] target = level.getTarget();
        float cornerRadius = Math.max(8f, cellSize * 0.18f);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                float left = gridOffsetX + col * cellSize;
                float top = gridOffsetY + row * cellSize;
                float inset = Math.max(2f, cellSize * 0.06f);
                RectF rect = new RectF(left + inset, top + inset,
                        left + cellSize - inset, top + cellSize - inset);

                int targetColor = target[row][col];

                if (targetColor == PuzzleLevel.COLOR_EMPTY) {
                    cellPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_cell_empty));
                } else if (level.isShowHint() && !locked[row][col]
                        && placed[row][col] == PuzzleLevel.COLOR_EMPTY) {
                    cellPaint.setColor(PuzzleColorUtils.getHintColorForId(getContext(), targetColor));
                } else if (placed[row][col] != PuzzleLevel.COLOR_EMPTY) {
                    cellPaint.setColor(PuzzleColorUtils.getColorForId(getContext(), placed[row][col]));
                } else {
                    cellPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_cell_target));
                }

                cellPaint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellPaint);

                if (locked[row][col]) {
                    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, lockedPaint);
                } else {
                    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, borderPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        updateGridMetrics();
        if (event.getAction() == MotionEvent.ACTION_UP && listener != null) {
            int col = (int) ((event.getX() - gridOffsetX) / cellSize);
            int row = (int) ((event.getY() - gridOffsetY) / cellSize);
            if (isValidCell(row, col)) {
                listener.onCellTapped(row, col);
            }
        }
        return true;
    }

    public int[] getCellAt(float x, float y) {
        updateGridMetrics();
        int col = (int) ((x - gridOffsetX) / cellSize);
        int row = (int) ((y - gridOffsetY) / cellSize);
        if (isValidCell(row, col)) {
            return new int[]{row, col};
        }
        return null;
    }

    public float getCellCenterX(int col) {
        return gridOffsetX + col * cellSize + cellSize / 2f;
    }

    public float getCellCenterY(int row) {
        return gridOffsetY + row * cellSize + cellSize / 2f;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
    }
}
