package com.rosberry.sample.surfaceviewrxed.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import io.reactivex.disposables.Disposable
import java.util.concurrent.Semaphore

/**
 * @author mmikhailov on 28/03/2019.
 */
open class DrivenSurfaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle), SurfaceHolder.Callback {

    private var canvasHandler: CanvasHandler? = null
    private var canvasHandlerDisposable: Disposable? = null
    private var thread: SurfaceThread? = null
    private var surfaceCreated = false

    init {
        with(holder) {
            addCallback(this@DrivenSurfaceView)
            setFormat(PixelFormat.TRANSLUCENT)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("SurfaceView", "surfaceCreated::")
        surfaceCreated = true
        start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("SurfaceView", "surfaceChanged::format: $format, w: $width, h: $height")

        thread?.requestDraw(true)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("SurfaceView", "surfaceDestroyed::")
        surfaceCreated = false
        stop()
    }

    fun setCanvasHandler(handler: CanvasHandler?) {
        Log.d("SurfaceView", "setCanvasHandler::")
        this.canvasHandler = handler

        stop()
        start()
    }

    private fun start() {
        if (!surfaceCreated) return

        synchronized(this) {
            Log.d("SurfaceView", "start::prev thread: $thread, canvasHandler: $canvasHandler")
            if (thread == null && canvasHandler != null) {
                thread = SurfaceThread(canvasHandler!!)
                    .apply { start() }

                subscribeComposer()
            }
        }
    }

    private fun stop() {
        synchronized(this) {
            Log.d("SurfaceView", "stop::current thread: $thread")
            if (thread != null) {
                canvasHandlerDisposable?.dispose()
                thread?.requestRelease()
                thread = null
            }
        }
    }

    private fun subscribeComposer() {
        canvasHandlerDisposable = canvasHandler?.drawCommands
            ?.subscribe { thread?.requestDraw(it) }
    }

    inner class SurfaceThread(
            private val canvasHandler: CanvasHandler
    ) : HandlerThread(
            SurfaceThread::class.java.simpleName,
            Process.THREAD_PRIORITY_AUDIO
    ), Handler.Callback {

        private val ACTION_START = 1
        private val ACTION_STOP = 0

        private var started = false
        private var canvasLocked = false
        private var canvas: Canvas? = null

        @Volatile
        private var currentPermits = 0
        private val permitsToDraw = 1
        private val semaphore = Semaphore(currentPermits)

        private lateinit var handler: Handler

        override fun start() {
            super.start()
            handler = Handler(looper, this).apply {
                sendMessage(obtainMessage(ACTION_START))
            }
        }

        override fun handleMessage(msg: Message): Boolean {
            if (!isAlive) {
                Log.d(SurfaceThread::class.java.simpleName, "dead thread... Cannot proceed")
                return false
            }

            when (msg.what) {
                ACTION_START -> startInternal()
                ACTION_STOP -> releaseInternal()
            }

            return true
        }

        fun requestRelease() {
            with(handler) { sendMessage(obtainMessage(ACTION_STOP)) }
        }

        fun requestDraw(draw: Boolean) {
            Log.d("SurfaceThread", "requestDraw::$draw, thread: ${Thread.currentThread()}")

            if (draw) {
                currentPermits = permitsToDraw
                semaphore.release()
            } else {
                currentPermits = 0
            }
        }

        private fun startInternal() {
            Log.d("SurfaceThread",
                    "startInternal::was started: $started, thread: ${Thread.currentThread()}")
            if (started) return
            started = true

            var pendingPost = false

            while (true) {
                semaphore.acquire(permitsToDraw)

                if (holder.surface.isValid.not()) continue

                if (pendingPost) {
                    pendingPost = false
                    unlockCanvasAndPost()
                } else {
                    pendingPost = lockCanvasAndDraw()
                }

                semaphore.release(currentPermits)
            }
        }

        private fun releaseInternal() {
            unlockCanvasAndPost()
            interrupt()
            quit()
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
                canvasHandler.onCanvas(canvas!!)

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