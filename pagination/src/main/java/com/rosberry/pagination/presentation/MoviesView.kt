package com.rosberry.pagination.presentation

import com.rosberry.pagination.system.MovieItems
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author Alexei Korshun on 28/09/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MoviesView : MvpView {

    fun showEmptyProgress(show: Boolean)

    fun showEmptyError(show: Boolean, errorText: String?)

    fun showEmptyView(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(errorDescription: String?)

    fun showRefreshProgress(show: Boolean)

    fun showPageProgress(show: Boolean)

    fun showData(show: Boolean, data: MovieItems)
}