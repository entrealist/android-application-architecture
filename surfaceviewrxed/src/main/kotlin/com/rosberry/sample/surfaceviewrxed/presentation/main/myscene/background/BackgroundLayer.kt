package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class BackgroundLayer: Layer<BackgroundState>() {

    init {
        setRenderables(BackgroundRenderable())
    }

    override val state = BackgroundState()

    override fun onStateChange(updated: BackgroundState) {

    }
}
