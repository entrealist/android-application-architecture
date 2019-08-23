package com.rosberry.sample.surfaceviewrxed.ui.main.system

import android.graphics.Canvas
import io.reactivex.Observable

/**
 * @author mmikhailov on 28/03/2019.
 */
interface CanvasHandler {

    val drawCommands: Observable<Boolean>

    fun onCanvas(canvas: Canvas)
}