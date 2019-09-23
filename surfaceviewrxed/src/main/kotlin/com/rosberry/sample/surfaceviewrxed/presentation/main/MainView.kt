package com.rosberry.sample.surfaceviewrxed.presentation.main

import android.graphics.PointF
import android.view.MotionEvent
import com.alexvasilkov.gestures.State
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
import io.reactivex.Observable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author mmikhailov on 28/03/2019.
 */
interface MainView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun registerSurfaceEvents(
            statesDeck: (Observable<State>) -> Unit,
            tapsDeck: (Observable<MotionEvent>) -> Unit,
            viewportCenterDeck: (PointF) -> Unit
    )

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSceneParams(sceneParams: SceneParams)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun animateStateTo(state: State)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun closeScope()
}