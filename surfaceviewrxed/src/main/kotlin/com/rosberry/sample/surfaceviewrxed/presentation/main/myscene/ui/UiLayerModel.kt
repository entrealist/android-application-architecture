package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerModel

class UiLayerModel : LayerModel<UiState>() {

    init {
        setRenderables(UiRenderable())
    }

    override val state = UiState()

    override fun onStateChange() {
    }
}
