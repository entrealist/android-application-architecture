package com.rosberry.sample.errorhandler.presentation

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author Alexei Korshun on 19/10/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ErrorView : MvpView {

    fun showResult(result: String)

    fun showError(title: String, description: String)
}