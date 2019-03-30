package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerModel

class GroundLayerModel : LayerModel<GroundState>() {

    init {
        setRenderables(BackgroundRenderable(), GridRenderable())
    }

    override val state = GroundState()

    override fun onStateChange() {

    }
}
