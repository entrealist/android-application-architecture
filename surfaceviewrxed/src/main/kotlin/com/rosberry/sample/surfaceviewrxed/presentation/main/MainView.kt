package com.rosberry.sample.surfaceviewrxed.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author mmikhailov on 28/03/2019.
 */
interface MainView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun registerSurfaceStates()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun closeScope()
}