package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid

import android.support.annotation.ColorInt
import com.alexvasilkov.gestures.State
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class GridState : State(), LayerState {

    var boardWidth: Float = 0f
    var boardHeight: Float = 0f
    var gridCellSizeNominal: Float = 0f
    @ColorInt
    var gridColor = 0
    var gridThick = 0f

    val gridCellSize: Float
        get() = gridCellSizeNominal * zoom

    override fun update(other: LayerState) {
        if (other is GridState) {
            super.set(other)

            this.boardWidth = other.boardWidth
            this.boardHeight = other.boardHeight
            this.gridCellSizeNominal = other.gridCellSizeNominal
            this.gridColor = other.gridColor
            this.gridThick = other.gridThick
        }
    }
}