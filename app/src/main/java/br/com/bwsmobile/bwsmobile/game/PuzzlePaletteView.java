package br.com.bwsmobile.bwsmobile.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

import br.com.bwsmobile.bwsmobile.R;

public class PuzzlePaletteView extends View {

    public interface PaletteListener {
        void onPiecePicked(int colorId);

        void onPieceDragMove(int colorId, float rawX, float rawY);

        void onPieceDropped(int colorId, float rawX, float rawY);

        void onPieceReleased();
    }

    private final Map<Integer, Integer> remaining = new HashMap<Integer, Integer>();
    private PaletteListener listener;

    private int[] availableColors = new int[0];
    private float pieceSize;
    private int draggingColor = PuzzleLevel.COLOR_EMPTY;
    private float dragX;
    private float dragY;

    private final Paint piecePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint disabledPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PuzzlePaletteView(Context context) {
        super(context);
        init();
    }

    public PuzzlePaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PuzzlePaletteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f);
        borderPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_grid_border));

        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_text_dark));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        disabledPaint.setColor(ContextCompat.getColor(getContext(), R.color.puzzle_cell_empty));
        disabledPaint.setStyle(Paint.Style.FILL);
    }

    public void setListener(PaletteListener listener) {
        this.listener = listener;
    }

    public void setupForLevel(PuzzleLevel level) {
        remaining.clear();
        int[][] target = level.getTarget();

        for (int row = 0; row < PuzzleLevel.GRID_SIZE; row++) {
            for (int col = 0; col < PuzzleLevel.GRID_SIZE; col++) {
                int color = target[row][col];
                if (color != PuzzleLevel.COLOR_EMPTY) {
                    Integer count = remaining.get(color);
                    remaining.put(color, count == null ? 1 : count + 1);
                }
            }
        }

        availableColors = new int[remaining.size()];
        int i = 0;
        for (Integer color : remaining.keySet()) {
            availableColors[i++] = color;
        }
        invalidate();
    }

    public boolean usePiece(int colorId) {
        Integer count = remaining.get(colorId);
        if (count == null || count <= 0) {
            return false;
        }
        remaining.put(colorId, count - 1);
        invalidate();
        return true;
    }

    public void returnPiece(int colorId) {
        Integer count = remaining.get(colorId);
        if (count == null) {
            remaining.put(colorId, 1);
        } else {
            remaining.put(colorId, count + 1);
        }
        invalidate();
    }

    public int getRemaining(int colorId) {
        Integer count = remaining.get(colorId);
        return count == null ? 0 : count;
    }

    public int getDraggingColor() {
        return draggingColor;
    }

    public void clearDrag() {
        draggingColor = PuzzleLevel.COLOR_EMPTY;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (availableColors.length == 0) {
            return;
        }

        float padding = getWidth() * 0.03f;
        float totalWidth = getWidth() - padding * 2;
        pieceSize = Math.min(totalWidth / availableColors.length - 16f, getHeight() * 0.65f);
        textPaint.setTextSize(pieceSize * 0.35f);

        float startX = (getWidth() - (availableColors.length * (pieceSize + 16f) - 16f)) / 2f;
        float centerY = getHeight() * 0.42f;

        for (int i = 0; i < availableColors.length; i++) {
            int colorId = availableColors[i];
            int count = getRemaining(colorId);
            float cx = startX + i * (pieceSize + 16f) + pieceSize / 2f;

            RectF rect = new RectF(cx - pieceSize / 2f, centerY - pieceSize / 2f,
                    cx + pieceSize / 2f, centerY + pieceSize / 2f);

            if (count > 0) {
                piecePaint.setColor(PuzzleColorUtils.getColorForId(getContext(), colorId));
                piecePaint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rect, 16f, 16f, piecePaint);
                canvas.drawRoundRect(rect, 16f, 16f, borderPaint);
            } else {
                canvas.drawRoundRect(rect, 16f, 16f, disabledPaint);
                canvas.drawRoundRect(rect, 16f, 16f, borderPaint);
            }

            canvas.drawText(String.valueOf(count), cx, centerY + pieceSize * 0.55f, textPaint);
        }

        if (draggingColor != PuzzleLevel.COLOR_EMPTY) {
            RectF dragRect = new RectF(dragX - pieceSize / 2f, dragY - pieceSize / 2f,
                    dragX + pieceSize / 2f, dragY + pieceSize / 2f);
            piecePaint.setColor(PuzzleColorUtils.getColorForId(getContext(), draggingColor));
            piecePaint.setAlpha(220);
            canvas.drawRoundRect(dragRect, 16f, 16f, piecePaint);
            piecePaint.setAlpha(255);
            canvas.drawRoundRect(dragRect, 16f, 16f, borderPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int picked = getColorAt(event.getX(), event.getY());
                if (picked != PuzzleLevel.COLOR_EMPTY && getRemaining(picked) > 0) {
                    draggingColor = picked;
                    dragX = event.getX();
                    dragY = event.getY();
                    if (listener != null) {
                        listener.onPiecePicked(picked);
                    }
                    invalidate();
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if (draggingColor != PuzzleLevel.COLOR_EMPTY) {
                    dragX = event.getX();
                    dragY = event.getY();
                    if (listener != null) {
                        listener.onPieceDragMove(draggingColor, event.getRawX(), event.getRawY());
                    }
                    invalidate();
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (draggingColor != PuzzleLevel.COLOR_EMPTY) {
                    if (listener != null) {
                        listener.onPieceDropped(draggingColor, event.getRawX(), event.getRawY());
                    }
                    draggingColor = PuzzleLevel.COLOR_EMPTY;
                    if (listener != null) {
                        listener.onPieceReleased();
                    }
                    invalidate();
                }
                return true;

            default:
                return super.onTouchEvent(event);
        }
    }

    private int getColorAt(float x, float y) {
        if (availableColors.length == 0) {
            return PuzzleLevel.COLOR_EMPTY;
        }

        float padding = getWidth() * 0.03f;
        float totalWidth = getWidth() - padding * 2;
        pieceSize = Math.min(totalWidth / availableColors.length - 16f, getHeight() * 0.65f);
        float startX = (getWidth() - (availableColors.length * (pieceSize + 16f) - 16f)) / 2f;
        float centerY = getHeight() * 0.42f;

        for (int i = 0; i < availableColors.length; i++) {
            float cx = startX + i * (pieceSize + 16f) + pieceSize / 2f;
            RectF rect = new RectF(cx - pieceSize / 2f, centerY - pieceSize / 2f,
                    cx + pieceSize / 2f, centerY + pieceSize / 2f);
            if (rect.contains(x, y) && getRemaining(availableColors[i]) > 0) {
                return availableColors[i];
            }
        }
        return PuzzleLevel.COLOR_EMPTY;
    }
}
