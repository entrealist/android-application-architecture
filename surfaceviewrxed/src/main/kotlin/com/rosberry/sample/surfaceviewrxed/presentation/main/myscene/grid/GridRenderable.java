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
        final float boardWidth = state.getBoardWidth();
        final float boardHeight = state.getBoardHeight();
        final float gridCellWidth = state.getCellWidth();
        final float gridCellHeight = state.getCellHeight();
        final float boardStartX = state.getX();
        final float boardStartY = state.getY();

        final int partsPerLine = 4;
        final int columnsBeforeX = (int) (boardStartX / gridCellWidth);
        final int columnsBeforeY = (int) (boardStartY / gridCellHeight);
        final int columnCount = (int) (canvasW / gridCellWidth + 3); // s zapasom
        final int rowCount = (int) (canvasH / gridCellHeight) + 3;
        final int linePartsCount = (columnCount + rowCount) * partsPerLine;

        final float[] gridLines = new float[linePartsCount];

        float mostEndLineX = canvasW;
        float mostEndLineY = canvasH;

        // gather columns parts
        for (int i = 0; i < columnCount; i++) {
            final int startIndex = i * partsPerLine;
            final float lineX = boardStartX + (i - columnsBeforeX) * gridCellWidth;

            // restrict by start side
            if (lineX < boardStartX) continue;

            // restrict by end side
            final float currentEndX = -boardStartX + lineX;
            if (currentEndX > boardWidth + 0.001f) { // suffering from float accuracy...
                mostEndLineX = gridLines[startIndex - 2];
                break;
            }

            // save column parts
            gridLines[startIndex] = gridLines[startIndex + 2] = lineX; // x of start & stop points
            gridLines[startIndex + 1] = boardStartY > 0 ? boardStartY : 0f; // y of start point
            gridLines[startIndex + 3] = mostEndLineY; // y of stop point
        }

        // gather rows parts
        for (int j = 0; j < rowCount; j++) {
            final int startIndex = (j + columnCount) * partsPerLine;
            final float lineY = boardStartY + (j - columnsBeforeY) * gridCellHeight;

            // restrict by top side
            if (lineY < boardStartY) continue;

            // restrict by bottom side
            final float currentBottomY = -boardStartY + lineY;
            if (currentBottomY > boardHeight + 0.001f) {
                mostEndLineY = gridLines[startIndex - 1];
                break;
            }

            // save row parts
            gridLines[startIndex] = boardStartX > 0 ? boardStartX : 0f; // x of start point
            gridLines[startIndex + 1] = gridLines[startIndex + 3] = lineY; // y of start & end points
            gridLines[startIndex + 2] = mostEndLineX; // x of end point
        }

        // apply Y restrictions to column lines, if applicable
        if (mostEndLineY != canvasH) {
            for (int k = 0; k < columnCount; k++) {
                if (gridLines[k * partsPerLine]
                        + gridLines[k * partsPerLine + 1]
                        + gridLines[k * partsPerLine + 2]
                        + gridLines[k * partsPerLine + 3] != 0) {

                    gridLines[k * partsPerLine + 3] = mostEndLineY;
                }
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
