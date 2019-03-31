package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground

import android.graphics.Color
import android.support.annotation.ColorInt
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class GroundLayer(
        model: LayerModel
) : Layer<GroundState>() {

    init {
        setRenderables(BackgroundRenderable(),
                GridRenderable(model))
    }

    override val state = GroundState()

    override fun onStateChange(updated: GroundState) {

    }

    data class LayerModel(
            val sceneWidth: Float,
            val sceneHeight: Float
    ) {
        var gridCellSize = 56f
        var gridThick = 2f
        @ColorInt
        var gridColor = Color.RED
    }
}
