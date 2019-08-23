package com.rosberry.sample.surfaceviewrxed.presentation.main

import android.graphics.PointF
import android.view.MotionEvent
import com.alexvasilkov.gestures.State
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
import io.reactivex.Observable

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