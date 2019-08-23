package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import android.graphics.Canvas
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class BackgroundLayer: Layer<BackgroundState> {

    override val state = BackgroundState()
    override val renderables = listOf(BackgroundRenderable())

    override fun changeState(updated: BackgroundState) {
        state.update(updated)
    }

    override fun draw(canvas: Canvas) {
        renderables.forEach { r ->
            r.draw(canvas, state)
        }
    }
}
