package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene

import android.graphics.Canvas
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background.BackgroundLayer
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background.BackgroundState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid.GridLayer
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid.GridState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui.UiLayer
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui.UiState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.StateObserver
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @author mmikhailov on 28/03/2019.
 */
class MySceneComposer : StateObserver(), CanvasHandler {

    private val backgroundLayer = BackgroundLayer()
    private val gridLayer = GridLayer()
    private val uiLayer = UiLayer()

    private val lock = Any()

    override val drawCommands: Observable<Boolean> = states
        .map { true } // we want to draw because of new state
        .timeout( // we don't want draw when there aren't new states within timeout
                100, TimeUnit.MILLISECONDS,
                Observable.just(false).concatWith(Observable.error(Exception()))
        )
        .retry()
        .distinctUntilChanged()

    /**
     * Called when canvas is ready for drawing
     *
     * Let draw the background first (0), then grid (1) and then UI (2)
     */
    override fun onCanvas(canvas: Canvas) {
        synchronized(lock) {
            backgroundLayer.draw(canvas)
            gridLayer.draw(canvas)
            uiLayer.draw(canvas)
        }
    }

    override fun acceptState(state: LayerState) {
        synchronized(lock) {
            when (state) {
                is BackgroundState -> backgroundLayer.changeState(state)
                is GridState -> gridLayer.changeState(state)
                is UiState -> uiLayer.changeState(state)
            }
        }
    }
}
