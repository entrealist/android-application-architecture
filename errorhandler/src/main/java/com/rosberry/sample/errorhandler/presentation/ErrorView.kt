package com.rosberry.sample.errorhandler.presentation

/**
 * @author Alexei Korshun on 19/10/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ErrorView : MvpView {

    fun showResult(result: String)

    fun showError(title: String, description: String)
}