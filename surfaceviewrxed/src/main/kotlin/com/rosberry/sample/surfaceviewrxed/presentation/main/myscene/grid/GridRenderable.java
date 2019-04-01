package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid;

import android.graphics.Canvas;
import android.graphics.Paint;

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

        final int partsPerLine = 4;
        final int columnsBeforeX = (int) (drawportX / gridCellSize);
        final int columnsBeforeY = (int) (drawportY / gridCellSize);
        final int columnCount = (int) (canvasW / gridCellSize) + Math.abs(columnsBeforeX) + 1;
        final int rowCount = (int) (canvasH / gridCellSize) + Math.abs(columnsBeforeY) + 1;
        final int linePartsCount = (columnCount + rowCount) * partsPerLine;

        final float[] gridLines = new float[linePartsCount];
        // gather columns parts
        for (int i = 0; i < columnCount; i++) {
            int startIndex = i * partsPerLine;

            gridLines[startIndex] = gridLines[startIndex + 2] = drawportX + (i - columnsBeforeX) * gridCellSize; // x of start & stop points
            gridLines[startIndex + 1] = 0f; // y of start point
            gridLines[startIndex + 3] = canvasH; // y of stop point
        }

        // gather rows parts
        for (int j = 0; j < rowCount; j++) {
            int startIndex = (j + columnCount) * partsPerLine;

            gridLines[startIndex] = 0f; // x of start point
            gridLines[startIndex + 1] = gridLines[startIndex + 3] = drawportY + (j - columnsBeforeY) * gridCellSize; // y of start & end points
            gridLines[startIndex + 2] = canvasW; // x of end point
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
