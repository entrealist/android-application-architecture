package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

import android.graphics.Canvas

/**
 * @author mmikhailov on 29/03/2019.
 */
interface Layer<T : LayerState> {

    val state: T
    val renderables: List<Renderable<T>>

    fun changeState(updated: T)
    fun draw(canvas: Canvas)
}