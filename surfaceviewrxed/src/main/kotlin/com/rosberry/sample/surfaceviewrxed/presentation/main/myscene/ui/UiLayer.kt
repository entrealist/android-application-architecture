package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class UiLayer : Layer<UiState>() {

    init {
        setRenderables(UiRenderable())
    }

    override val state = UiState()

    override fun onStateChange(updated: UiState) {
    }
}
