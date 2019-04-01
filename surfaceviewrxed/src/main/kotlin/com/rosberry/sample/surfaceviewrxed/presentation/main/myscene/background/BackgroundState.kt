package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import android.graphics.Color
import android.support.annotation.ColorInt
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class BackgroundState : LayerState {

    @ColorInt
    var backgroundColor = Color.WHITE

    override fun update(other: LayerState) {
        if (other is BackgroundState) {
            this.backgroundColor = other.backgroundColor
        }
    }
}