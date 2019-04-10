package com.rosberry.sample.surfaceviewrxed.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.State
import com.alexvasilkov.gestures.views.interfaces.GestureView
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams

/**
 * @author mmikhailov on 28/03/2019.
 */
class GestureSurfaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : DrivenSurfaceView(context, attrs, defStyle), GestureView {

    private val controller = GestureController(this).apply {
        settings.apply {
            isZoomEnabled = true
            isRotationEnabled = false
            isDoubleTapEnabled = true
            fitMethod = Settings.Fit.NONE
            boundsType = Settings.Bounds.PIVOT
            isExitEnabled = false
            isFillViewport = false
        }
    }

    override fun getController() = controller

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        controller.settings.setViewport(width, height)
        controller.updateState()

        super.surfaceChanged(holder, format, width, height)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return controller.onTouch(this, event)
    }

    fun setSceneParams(sceneParams: SceneParams) {
        with(controller.settings) {
            setImage(sceneParams.width.toInt(), sceneParams.height.toInt())
            overzoomFactor = sceneParams.overzoomFactor
            maxZoom = sceneParams.maxZoom
            minZoom = sceneParams.minZoom
            animationsDuration = sceneParams.gestureAnimationDuration
        }

        controller.updateState()
    }

    fun animateStateTo(state: State) {
        controller.animateStateTo(state)
    }
}