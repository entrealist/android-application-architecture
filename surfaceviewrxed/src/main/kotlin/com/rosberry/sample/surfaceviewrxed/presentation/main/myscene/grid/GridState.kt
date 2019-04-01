package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid

import android.graphics.Color
import android.support.annotation.ColorInt
import com.alexvasilkov.gestures.State
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class GridState : State(), LayerState {

    var gridCellSize = 56f
    var gridThick = 2f
    @ColorInt
    var gridColor = Color.RED


    override fun update(other: LayerState) {
        if (other is GridState) {
            super.set(other)

            this.gridCellSize = other.gridCellSize
            this.gridThick = other.gridThick
            this.gridColor = other.gridColor
        }
    }
}