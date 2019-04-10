package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid

import android.graphics.Matrix
import android.support.annotation.ColorInt
import com.alexvasilkov.gestures.State
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class GridState : State(), LayerState {

    var boardWidthNominal: Float = 0f
    var boardHeightNominal: Float = 0f
    var gridCellWidthNominal: Float = 0f
    var gridCellHeightNominal: Float = 0f
    var gridThickNominal: Float = 0f

    @ColorInt
    var gridColor = 0

    val gridThick: Int
        get() = (gridThickNominal * zoom / 2).toInt()

    val boardWidth: Float
        get() = boardWidthNominal * zoom

    val boardHeight: Float
        get() = boardHeightNominal * zoom

    val cellWidth: Float
        get() = gridCellWidthNominal * zoom

    val cellHeight: Float
        get() = gridCellHeightNominal * zoom

    override fun update(other: LayerState) {
        if (other is GridState) {
            super.set(other)

            this.boardWidthNominal = other.boardWidthNominal
            this.boardHeightNominal = other.boardHeightNominal
            this.gridCellWidthNominal = other.gridCellWidthNominal
            this.gridCellHeightNominal = other.gridCellHeightNominal
            this.gridColor = other.gridColor
            this.gridThickNominal = other.gridThickNominal
        }
    }

    fun zoomToCell(pivotX: Float, pivotY: Float, screenX: Float, screenY: Float, zoom: Float) {
        val startBorder = x
        val topBorder = y
        val endBorder = x + boardWidth
        val bottomBorder = y + boardHeight

        if (screenX in startBorder..endBorder
                && screenY in topBorder..bottomBorder) {

            var cellX = 0f
            var cellY = 0f

            var cellBorderX = startBorder + cellWidth
            while (cellBorderX <= endBorder + 0.001) {
                if (screenX <= cellBorderX) {
                    cellX = cellBorderX - cellWidth / 2
                    break
                }

                cellBorderX += cellWidth
            }

            var cellBorderY = topBorder + cellHeight
            while (cellBorderY <= bottomBorder + 0.001) {
                if (screenY <= cellBorderY) {
                    cellY = cellBorderY - cellHeight / 2
                    break
                }

                cellBorderY += cellHeight
            }

            val dx = pivotX - cellX
            val dy = pivotY - cellY
            val matrix = Matrix()

            get(matrix)
            matrix.postScale(zoom, zoom, cellX, cellY)
            matrix.postTranslate(dx, dy)
            set(matrix)
        }
    }
}