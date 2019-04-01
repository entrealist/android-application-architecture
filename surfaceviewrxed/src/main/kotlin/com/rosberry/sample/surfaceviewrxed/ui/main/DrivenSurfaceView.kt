package com.rosberry.sample.surfaceviewrxed.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.Settings
import com.alexvasilkov.gestures.views.interfaces.GestureView
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import io.reactivex.disposables.Disposable

/**
 * @author mmikhailov on 28/03/2019.
 */
class DrivenSurfaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle), SurfaceHolder.Callback, GestureView {

    private val controller = GestureController(this)
    private var canvasHandler: CanvasHandler? = null
    private var canvasHandlerDisposable: Disposable? = null
    private var thread: SurfaceThread? = null
    private var surfaceCreated = false

    init {
        with(holder) {
            addCallback(this@DrivenSurfaceView)
            setFormat(PixelFormat.TRANSLUCENT)
        }

        controller.settings.apply {
            isZoomEnabled = true
            isRotationEnabled = false
            isDoubleTapEnabled = true
            fitMethod = Settings.Fit.NONE
            boundsType = Settings.Bounds.NONE
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("Dbg.SurfaceView", "surfaceCreated::")
        surfaceCreated = true
        start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Dbg.SurfaceView", "surfaceChanged::format: $format, w: $width, h: $height")

        controller.settings.setViewport(width, height)
        controller.updateState()
        thread?.setDraw(true)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("Dbg.SurfaceView", "surfaceDestroyed::")
        surfaceCreated = false
        stop()
    }

    override fun getController() = controller

    @SuppressLint("ClickableViewAccessibility") // Will be handled by gestures controller
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return controller.onTouch(this, event)
    }

    fun setCanvasHandler(handler: CanvasHandler?) {
        Log.d("Dbg.SurfaceView", "setCanvasHandler::")
        this.canvasHandler = handler

        stop()
        start()
    }

    private fun start() {
        if (!surfaceCreated) return

        synchronized(this) {
            Log.d("Dbg.SurfaceView", "start::prev thread: $thread, canvasHandler: $canvasHandler")
            if (thread == null && canvasHandler != null) {
                thread = SurfaceThread(canvasHandler!!)
                    .apply { start() }

                subscribeComposer()
            }
        }
    }

    private fun stop() {
        synchronized(this) {
            Log.d("Dbg.SurfaceView", "stop::current thread: $thread")
            if (thread != null) {
                canvasHandlerDisposable?.dispose()
                thread?.release()
                thread = null
            }
        }
    }

    private fun subscribeComposer() {
        canvasHandlerDisposable = canvasHandler?.drawCommands
            ?.subscribe { thread?.setDraw(it) }
    }

    inner class SurfaceThread(private val handler: CanvasHandler) : Thread() {
        private var running = true
        private var canvasLocked = false
        private var canvas: Canvas? = null
        private var draw = false

        /**
         * Sets whether is there draw something
         */
        fun setDraw(draw: Boolean) {
            Log.d("Dbg.SurfaceThread", "setDraw::$draw")
            this.draw = draw
        }

        override fun run() {
            Log.d("Dbg.SurfaceThread", "run::starting thread: $this")
            var pendingPost = false

            while (running) {

                if (holder.surface.isValid.not()) continue

                if (!draw && !pendingPost) continue

                if (pendingPost) {
                    pendingPost = false
                    unlockCanvasAndPost()
                } else {
                    pendingPost = lockCanvasAndDraw()
                }
            }

            unlockCanvasAndPost()

            running = false
        }

        internal fun release() {
            Log.d("Dbg.SurfaceThread", "release")
            running = false
        }

        private fun unlockCanvasAndPost() {
            if (canvasLocked && canvas != null) {
                canvasLocked = false
                holder.unlockCanvasAndPost(canvas)
                canvas = null
            }
        }

        private fun lockCanvasAndDraw(): Boolean {
            canvas = holder.lockCanvas()

            return if (canvas != null) {
                canvasLocked = true
                handler.onCanvas(canvas!!)

                true

            } else {
                try {
                    sleep(1)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                false
            }
        }
    }
}