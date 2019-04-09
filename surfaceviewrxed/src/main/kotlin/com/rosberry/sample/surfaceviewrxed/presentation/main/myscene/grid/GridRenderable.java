package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Renderable;

import org.jetbrains.annotations.NotNull;

/**
 * @author mmikhailov on 30/03/2019.
 */
public class GridRenderable implements Renderable<GridState> {

    private final Paint gridPaint = new Paint();

    private int paintColor;
    private float strokeWidth;

    public GridRenderable() {
        gridPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(@NotNull Canvas canvas, GridState state) {
        setPaint(state);

        final float canvasW = canvas.getWidth();
        final float canvasH = canvas.getHeight();
        final float drawportX = state.getX();
        final float drawportY = state.getY();
        final float gridCellSize = state.getGridCellSize();
        final float boardWidthScaled = Math.abs(state.getBoardWidth() * state.getZoom());
        final float boardHeightScaled = Math.abs(state.getBoardHeight() * state.getZoom());

        final int partsPerLine = 4;
        final int columnsBeforeX = (int) (drawportX / gridCellSize);
        final int columnsBeforeY = (int) (drawportY / gridCellSize);
        final int columnCount = (int) (canvasW / gridCellSize) + 2;
        final int rowCount = (int) (canvasH / gridCellSize) + 2;
        final int linePartsCount = (columnCount + rowCount) * partsPerLine;

        Log.d("Dbg.GridRenderable", "z:" + state.getZoom() + ", x: " + drawportX + ", y: " + drawportY + ", c: " + columnCount);
        final float[] gridLines = new float[linePartsCount];

        float mostEndLineX = canvasW;
        float mostEndLineY = canvasH;

        // gather columns parts
        for (int i = 0; i < columnCount; i++) {
            final int startIndex = i * partsPerLine;
            final float lineX = drawportX + (i - columnsBeforeX) * gridCellSize;

            // restrict by start side
            if (lineX < drawportX) continue;

            // restrict by end side
            final float boardEndSideX = Math.abs(drawportX) + lineX;
            if (boardEndSideX > boardWidthScaled) {
                mostEndLineX = gridLines[startIndex - 2];
                break;
            }

            gridLines[startIndex] = gridLines[startIndex + 2] = lineX; // x of start & stop points
            gridLines[startIndex + 1] = drawportY > 0 ? drawportY : 0f; // y of start point
            gridLines[startIndex + 3] = mostEndLineY; // y of stop point
        }

        // gather rows parts
        for (int j = 0; j < rowCount; j++) {
            final int startIndex = (j + columnCount) * partsPerLine;
            final float lineY = drawportY + (j - columnsBeforeY) * gridCellSize;

            // restrict by top side
            if (lineY < drawportY) continue;

            // restrict by bottom side
            final float boardBottomSideY = Math.abs(drawportY) + lineY;
            if (boardBottomSideY > boardHeightScaled) {
                mostEndLineY = gridLines[startIndex - 1];
                break;
            }

            gridLines[startIndex] = drawportX > 0 ? drawportX : 0f; // x of start point
            gridLines[startIndex + 1] = gridLines[startIndex + 3] = lineY; // y of start & end points
            gridLines[startIndex + 2] = mostEndLineX; // x of end point
        }

        // apply Y restrictions to column lines, if applicable
        if (mostEndLineY != canvasH) {
            for (int k = 0; k < columnCount; k++) {
                gridLines[k * partsPerLine + 3] = mostEndLineY;
            }
        }

        canvas.drawLines(gridLines, gridPaint);
    }

    private void setPaint(GridState state) {
        if (state.getGridColor() != paintColor) {
            paintColor = state.getGridColor();
            gridPaint.setColor(paintColor);
        }

        if (state.getGridThick() != strokeWidth) {
            strokeWidth = state.getGridThick();
            gridPaint.setStrokeWidth(strokeWidth);
        }
    }
}
