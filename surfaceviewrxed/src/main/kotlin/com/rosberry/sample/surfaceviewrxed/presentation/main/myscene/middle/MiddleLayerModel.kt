package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerModel

class MiddleLayerModel : LayerModel<MiddleState>() {

    init {
        setRenderables(CurveRenderable())
    }

    // the state of renderables
    override val state = MiddleState()

    override fun onStateChange() {

    }
}
