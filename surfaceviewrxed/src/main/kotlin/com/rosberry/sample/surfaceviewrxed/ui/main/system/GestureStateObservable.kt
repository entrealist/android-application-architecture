package com.rosberry.sample.surfaceviewrxed.ui.main.system

import androidx.annotation.CheckResult
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.State
import com.alexvasilkov.gestures.views.interfaces.GestureView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * @author mmikhailov on 31/03/2019.
 */
@CheckResult
fun GestureView.states(): Observable<State> {
    return GestureStateObservable(this)
}

class GestureStateObservable(
        private val view: GestureView
) : Observable<State>() {

    override fun subscribeActual(observer: Observer<in State>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.controller.addOnStateChangeListener(listener)
    }

    private class Listener(
            private val view: GestureView,
            private val observer: Observer<in State>
    ) : MainThreadDisposable(), GestureController.OnStateChangeListener {

        override fun onStateReset(oldState: State, newState: State) {
            if (!isDisposed) {
                try {
                    observer.onNext(newState)
                } catch (e: Exception) {
                    observer.onError(e)
                    dispose()
                }
            }
        }

        override fun onStateChanged(state: State) {
            if (!isDisposed) {
                try {
                    observer.onNext(state)
                } catch (e: Exception) {
                    observer.onError(e)
                    dispose()
                }
            }
        }

        override fun onDispose() {
            view.controller.removeOnStateChangeListener(this@Listener)
        }
    }
}