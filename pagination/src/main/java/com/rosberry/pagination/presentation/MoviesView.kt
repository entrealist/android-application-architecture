package com.rosberry.pagination.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rosberry.pagination.system.MovieItems

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