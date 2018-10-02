package com.rosberry.pagination.presentation

import com.arellomobile.mvp.MvpView

/**
 * @author Alexei Korshun on 28/09/2018.
 */
interface MoviesView : MvpView {

    fun showEmptyProgress(show: Boolean)
    fun showEmptyError(show: Boolean, errorText: String?)
    fun showEmptyView(show: Boolean)
    fun showError(errorDescription: String?)
    fun showRefreshProgress(show: Boolean)
    fun showPageProgress(show: Boolean)
    fun showData(show: Boolean, data: List<MovieItem>)
}