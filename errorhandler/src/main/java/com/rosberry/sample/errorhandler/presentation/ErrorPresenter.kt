package com.rosberry.sample.errorhandler.presentation

import com.rosberry.sample.errorhandler.domain.SampleInteractor
import com.rosberry.sample.errorhandler.entity.State

/**
 * @author Alexei Korshun on 19/10/2018.
 */
@InjectViewState
class ErrorPresenter(
        private val errorHandler: ErrorHandler,
        private val sampleInteractor: SampleInteractor
) : MvpPresenter<ErrorView>() {

    private val compositeDisposable = CompositeDisposable()

    fun proceed(checkedState: State) {
        sampleInteractor.proceed(checkedState)
            .subscribe(
                    { result ->
                        viewState.showResult(result)
                    },
                    { error ->
                        errorHandler.proceed(error) { title, description ->
                            viewState.showError(title, description)
                        }
                    }
            )
            .add()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun Disposable.add() {
        compositeDisposable.add(this)
    }
}