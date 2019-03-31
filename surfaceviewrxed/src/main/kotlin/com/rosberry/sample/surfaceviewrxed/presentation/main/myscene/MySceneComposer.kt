package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene

import android.graphics.Canvas
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground.GroundLayer
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground.GroundState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle.MiddleLayer
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle.MiddleState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui.UiLayer
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui.UiState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.StateHandler
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * @author mmikhailov on 28/03/2019.
 */
class MySceneComposer(
        sceneParams: SceneParams
) : CanvasHandler, StateHandler {

    private val stateStream = PublishSubject.create<LayerState>()
    private val groundLayer = GroundLayer(GroundLayer.LayerModel(sceneParams.width, sceneParams.height))
    private val middleLayer = MiddleLayer()
    private val uiLayer = UiLayer()

    private val pendingDrawLayerArray = Array(3) { false } // todo refactor...

    override val drawCommands: Observable<Boolean> = stateStream
        .doOnNext {
            when (it) {
                is GroundState -> {
                    pendingDrawLayerArray[0] = true
                    groundLayer.changeState(it)
                }
                is MiddleState -> {
                    pendingDrawLayerArray[1] = true
                    middleLayer.changeState(it)
                }
                is UiState -> {
                    pendingDrawLayerArray[2] = true
                    uiLayer.changeState(it)
                }
            }
        }
        .map { true }
        .timeout(
                100, TimeUnit.MILLISECONDS,
                Observable.just(false).concatWith(Observable.error(Exception()))
        )
        .doOnError {
            pendingDrawLayerArray[0] = false
            pendingDrawLayerArray[1] = false
            pendingDrawLayerArray[2] = false
        }
        .retry()
        .distinctUntilChanged()

    override fun onCanvas(canvas: Canvas) {
        if (pendingDrawLayerArray[0]) {
            groundLayer.drawLayer(canvas)
        }

        if (pendingDrawLayerArray[1]) {
            middleLayer.drawLayer(canvas)
        }

        if (pendingDrawLayerArray[2]) {
            uiLayer.drawLayer(canvas)
        }
    }

    override fun onState(state: LayerState) {
        stateStream.onNext(state)
    }
}
