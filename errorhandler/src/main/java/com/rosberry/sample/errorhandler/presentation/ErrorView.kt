package com.rosberry.sample.errorhandler.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Alexei Korshun on 19/10/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ErrorView : MvpView {

    fun showResult(result: String)

    fun showError(title: String, description: String)
}