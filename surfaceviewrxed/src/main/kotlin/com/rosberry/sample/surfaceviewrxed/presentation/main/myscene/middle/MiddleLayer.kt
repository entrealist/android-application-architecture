package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class MiddleLayer : Layer<MiddleState>() {

    init {
        setRenderables(CurveRenderable())
    }

    // the state of renderables
    override val state = MiddleState()

    override fun onStateChange(updated: MiddleState) {

    }
}
