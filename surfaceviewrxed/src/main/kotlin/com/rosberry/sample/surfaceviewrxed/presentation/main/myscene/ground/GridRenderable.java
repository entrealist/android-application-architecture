package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Renderable;

import org.jetbrains.annotations.NotNull;

/**
 * @author mmikhailov on 30/03/2019.
 */
public class GridRenderable implements Renderable<GroundState> {

    private final Paint gridPaint = new Paint();
    private final GroundLayer.LayerModel model;

    public GridRenderable(GroundLayer.LayerModel model) {
        this.model = model;

        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(model.getGridColor());
        gridPaint.setStrokeWidth(model.getGridThick());
    }

    @Override
    public void draw(@NotNull Canvas canvas, GroundState groundState) {
        final float canvasW = canvas.getWidth();
        final float canvasH = canvas.getHeight();
        final float drawportX = groundState.getX();
        final float drawportY = groundState.getY();

        final int partsPerLine = 4;
        final int columnsBeforeX = (int) (drawportX / model.getGridCellSize());
        final int columnsBeforeY = (int) (drawportY / model.getGridCellSize());
        final int columnCount = (int) (canvasW / model.getGridCellSize()) + columnsBeforeX + 1;
        final int rowCount = (int) (canvasH / model.getGridCellSize()) + columnsBeforeY + 1;
        final int linePartsCount = (columnCount + rowCount) * partsPerLine;

        final float[] gridLines = new float[linePartsCount];
        // gather columns parts
        for (int i = 0; i < columnCount; i++) {
            int startIndex = i * partsPerLine;

            gridLines[startIndex] = gridLines[startIndex + 2] = drawportX + (i - columnsBeforeX) * model.getGridCellSize(); // x of start & stop points
            gridLines[startIndex + 1] = 0f; // y of start point
            gridLines[startIndex + 3] = canvasH; // y of stop point
        }

        // gather rows parts
        for (int j = columnCount; j < columnCount + rowCount; j++) {
            int startIndex = j * partsPerLine;
            int currentRow = j - columnCount;

            gridLines[startIndex] = 0f; // x of start point
            gridLines[startIndex + 1] = gridLines[startIndex + 3] = drawportY + (currentRow - columnsBeforeY) * model.getGridCellSize(); // y of start & end points
            gridLines[startIndex + 2] = canvasW; // x of end point
        }

        canvas.drawLines(gridLines, gridPaint);
    }
}
