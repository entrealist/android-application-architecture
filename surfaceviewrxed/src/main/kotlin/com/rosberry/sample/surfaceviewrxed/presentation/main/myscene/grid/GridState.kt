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
    var textSizeNominal: Float = 0f

    @ColorInt
    var gridColor = 0

    @ColorInt
    var textColor = 0

    val textSize: Float
        get() = textSizeNominal * zoom

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

    val columns: Int
        get() = (boardWidthNominal / gridCellWidthNominal).toInt()

    val rows: Int
        get() = (boardHeightNominal / gridCellHeightNominal).toInt()

    val numbers = mutableSetOf<Triple<String, Int, Int>>()

    fun update(other: GridState) {
        super.set(other)

        this.boardWidthNominal = other.boardWidthNominal
        this.boardHeightNominal = other.boardHeightNominal
        this.gridCellWidthNominal = other.gridCellWidthNominal
        this.gridCellHeightNominal = other.gridCellHeightNominal
        this.gridColor = other.gridColor
        this.gridThickNominal = other.gridThickNominal
        this.numbers.addAll(other.numbers)
        this.textColor = other.textColor
        this.textSizeNominal = other.textSizeNominal
    }

    fun addNumber(screenX: Float, screenY: Float) {
        val startBorder = x
        val topBorder = y
        val endBorder = x + boardWidth
        val bottomBorder = y + boardHeight

        if (screenX in startBorder..endBorder
                && screenY in topBorder..bottomBorder) {

            var column = 0
            var row = 0

            for (i in 0..columns) {
                val curBorder = startBorder + cellWidth * (i + 1)
                if (screenX <= curBorder) {
                    column = i
                    break
                }
            }

            for (i in 0..rows) {
                val curBorder = topBorder + cellHeight * (i + 1)
                if (screenY <= curBorder) {
                    row = i
                    break
                }
            }

            //Log.d("Dbg.AddNumber", "column: $column, row: $row")

            numbers.add(Triple("$column:$row", column, row))
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
            while (cellBorderX <= endBorder + 0.1) {
                if (screenX <= cellBorderX) {
                    cellX = cellBorderX - cellWidth / 2
                    break
                }

                cellBorderX += cellWidth
            }

            var cellBorderY = topBorder + cellHeight
            while (cellBorderY <= bottomBorder + 0.1) {
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