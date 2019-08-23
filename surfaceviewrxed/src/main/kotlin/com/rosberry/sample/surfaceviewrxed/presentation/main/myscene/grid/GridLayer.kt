package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid

import android.graphics.Canvas
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class GridLayer : Layer<GridState> {

    override val state = GridState()
    override val renderables = listOf(GridRenderable(), NumbersRenderable())

    override fun changeState(updated: GridState) {
        state.update(updated)
    }

    override fun draw(canvas: Canvas) {
        renderables.forEach { r ->
            r.draw(canvas, state)
        }
    }
}
