package com.rosberry.sample.surfaceviewrxed.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.views.interfaces.GestureView
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.Constant.MAX_ZOOM
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.Constant.MIN_ZOOM
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.Constant.OVERZOOM_FACTOR

/**
 * @author mmikhailov on 28/03/2019.
 */
class GestureSurfaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : DrivenSurfaceView(context, attrs, defStyle), SurfaceHolder.Callback, GestureView {

    private val controller = GestureController(this).apply {
        settings.apply {
            isZoomEnabled = true
            isRotationEnabled = false
            isDoubleTapEnabled = true
            fitMethod = Settings.Fit.NONE
            boundsType = Settings.Bounds.NONE
            isExitEnabled = false
            overzoomFactor = OVERZOOM_FACTOR
            maxZoom = MAX_ZOOM
            minZoom = MIN_ZOOM
            animationsDuration = 150L
            setImage(1, 1)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        controller.settings.setViewport(width, height)
        controller.updateState()

        super.surfaceChanged(holder, format, width, height)
    }


    override fun getController() = controller

    @SuppressLint("ClickableViewAccessibility") // Will be handled by gestures controller
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return controller.onTouch(this, event)
    }
}