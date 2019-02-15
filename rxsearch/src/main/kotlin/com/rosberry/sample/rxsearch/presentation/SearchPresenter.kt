package com.rosberry.sample.rxsearch.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rosberry.sample.rxsearch.domain.ConnectivityInteractor
import com.rosberry.sample.rxsearch.domain.SearchInteractor
import com.rosberry.sample.rxsearch.error.EmptySearchResult
import com.rosberry.sample.rxsearch.error.NetworkUnavailableException
import com.rosberry.sample.rxsearch.presentation.model.SearchResultItem
import com.rosberry.sample.rxsearch.presentation.model.SearchResultItemConverter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author mmikhailov on 15.01.2019
 */
@InjectViewState
class SearchPresenter @Inject constructor(
        private val searchInteractor: SearchInteractor,
        private val connectivityInteractor: ConnectivityInteractor,
        private val searchItemConverter: SearchResultItemConverter
) : MvpPresenter<SearchView>() {

    private val compositeDisposable = CompositeDisposable()

    // should be 'Behaviour'subject due to preserve last emitted item
    private val queryStream = BehaviorSubject.create<String>()
    private val retryActionStream = PublishSubject.create<Unit>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        listenQuery()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun clickRetry() {
        retryActionStream.onNext(Unit)
    }

    fun clickResultItem(id: String) {
        viewState.showToast(id)
    }

    fun onQuery(newQuery: String) {
        queryStream.onNext(newQuery)
    }

    private fun listenQuery() {
        queryStream
            .doOnNext { if (it.isBlank()) showInitState() }
            .debounce(600, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { viewState.showProgress(true) }
            .switchMapSingle { query -> searchInteractor.search(query) }
            .map { searchItemConverter.convertList(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { showError(it) }
            .retryWhen { retryHandler ->
                retryHandler.flatMap { t ->
                    if (t is UnknownHostException || t is NetworkUnavailableException) {
                        connectivityInteractor.listenConnectionAvailability()
                    } else {
                        retryActionStream
                    }
                }
            }
            .subscribe(
                    { showResults(it) },
                    { it.printStackTrace() }
            )
            .apply { compositeDisposable.add(this) }
    }

    private fun showInitState() {
        viewState.showProgress(false)
        viewState.updateResultItems(emptyList())
        viewState.hideInteractivePlaceholder()
    }

    private fun showResults(items: List<SearchResultItem>) {
        viewState.showProgress(false)
        viewState.updateResultItems(items)

        if (items.isNotEmpty()) {
            viewState.hideInteractivePlaceholder()
        } else {
            showInteractivePlaceholder(EmptySearchResult())
        }
    }

    private fun showError(error: Throwable) {
        viewState.showProgress(false)
        viewState.updateResultItems(emptyList())
        showInteractivePlaceholder(error)
    }

    private fun showInteractivePlaceholder(error: Throwable) {
        // hint: use error message provider here to get messages
        var title = "Something went wrong"
        var description = "Please, try again later. "
        var actionText = "Retry"

        when (error) {
            is NetworkUnavailableException -> {
                title = "No internet connection"
                description = ""
                actionText = ""
            }
            is EmptySearchResult -> {
                title = "Found nothing"
                description = "Try to change your query"
                actionText = ""
            }
        }


        viewState.showInteractivePlaceholder(title, description, actionText)
    }
}