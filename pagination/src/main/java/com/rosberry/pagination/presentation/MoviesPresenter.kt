package com.rosberry.pagination.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rosberry.android.core.presentation.Paginator
import com.rosberry.pagination.data.MoviesRepository
import com.rosberry.pagination.entity.Movie
import com.rosberry.pagination.system.Movies

/**
 * @author Alexei Korshun on 28/09/2018.
 */
@InjectViewState
class MoviesPresenter constructor(
        private val moviesRepository: MoviesRepository
) : MvpPresenter<MoviesView>(), Paginator.ViewController<Movie> {

    private val paginator: Paginator<Movie> = Paginator({ moviesRepository.movies(it) }, this)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        paginator.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

    override fun showEmptyProgress(show: Boolean) {
        viewState.showEmptyProgress(show)
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        viewState.showEmptyError(show, "Error")
    }

    override fun showEmptyView(show: Boolean) {
        viewState.showEmptyView(show)
    }

    override fun showData(show: Boolean, data: Movies) {
        val newData = data.asSequence()
            .map { movie -> MovieItem(movie) }
            .toList()
        viewState.showData(show, newData)
    }

    override fun showErrorMessage(error: Throwable) {
        viewState.showError(error.localizedMessage)
    }

    override fun showRefreshProgress(show: Boolean) {
        viewState.showRefreshProgress(show)
    }

    override fun showPageProgress(show: Boolean) {
        viewState.showPageProgress(show)
    }

    fun onRefresh() = paginator.refresh()

    fun loadNextPage() = paginator.loadNewPage()
}