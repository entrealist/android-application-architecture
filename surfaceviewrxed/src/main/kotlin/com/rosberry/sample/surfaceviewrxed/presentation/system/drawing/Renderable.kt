package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

import android.graphics.Canvas

/**
 * @author mmikhailov on 30/03/2019.
 */
interface Renderable<in State> {

    fun draw(canvas: Canvas, state: State)
}