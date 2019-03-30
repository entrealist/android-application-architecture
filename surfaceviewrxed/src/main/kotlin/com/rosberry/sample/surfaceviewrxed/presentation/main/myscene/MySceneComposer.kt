package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene

import android.graphics.Canvas
import android.util.Log
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground.GroundLayerModel
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground.GroundState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle.MiddleLayerModel
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle.MiddleState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui.UiLayerModel
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
        private val sceneParams: SceneParams
) : CanvasHandler, StateHandler {

    private val stateStream = PublishSubject.create<LayerState>()
    private val groundLayer = GroundLayerModel()
    private val middleLayer = MiddleLayerModel()
    private val uiLayer = UiLayerModel()

    private val pendingLayerMapping = Array(3) { false } // todo refactor...

    override val drawCommands: Observable<Boolean> = stateStream
        .doOnNext {
            when (it) {
                is GroundState -> pendingLayerMapping[0] = true
                is MiddleState -> pendingLayerMapping[1] = true
                is UiState -> pendingLayerMapping[2] = true
            }
        }
        .map { true }
        .timeout(
                100, TimeUnit.MILLISECONDS,
                Observable.just(false).concatWith(Observable.error(Exception()))
        )
        .doOnError {
            pendingLayerMapping[0] = false
            pendingLayerMapping[1] = false
            pendingLayerMapping[2] = false
        }
        .retry()
        .distinctUntilChanged()

    override fun onCanvas(canvas: Canvas) {
        if (pendingLayerMapping[0]) {
            Log.d("Dbg.MySceneComposer", "onCanvas::ground")
            groundLayer.drawLayer(canvas)
        }

        if (pendingLayerMapping[1]) {
            Log.d("Dbg.MySceneComposer", "onCanvas::middle")
            middleLayer.drawLayer(canvas)
        }

        if (pendingLayerMapping[2]) {
            Log.d("Dbg.MySceneComposer", "onCanvas::ui")
            uiLayer.drawLayer(canvas)
        }
    }

    override fun onState(state: LayerState) {
        stateStream.onNext(state)

        when (state) {
            is GroundState -> groundLayer.changeState(state)
            is MiddleState -> middleLayer.changeState(state)
            is UiState -> uiLayer.changeState(state)
        }
    }
}
