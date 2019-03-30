package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

import android.graphics.Canvas

/**
 * @author mmikhailov on 29/03/2019.
 */
abstract class LayerModel<T : LayerState> {

    protected abstract val state: T

    abstract fun onStateChange()

    private val renderables: MutableList<Renderable<T>> = mutableListOf()

    fun changeState(updated: T) {
        state.set(updated)

        onStateChange()
    }

    fun drawLayer(canvas: Canvas) {
        renderables.forEach { r ->
            r.draw(canvas, state)
        }
    }

    protected fun setRenderables(vararg renderable: Renderable<T>) {
        renderables.addAll(renderable)
    }
}