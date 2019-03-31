package com.rosberry.sample.surfaceviewrxed.ui.main.system

import android.os.Looper
import android.support.annotation.CheckResult
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.State
import com.alexvasilkov.gestures.views.interfaces.GestureView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposables

/**
 * @author mmikhailov on 31/03/2019.
 */
@CheckResult
@JvmOverloads
fun GestureView.states(
        lambda: (State) -> Unit = { }
): Observable<State> {
    return GestureStateObservable(this, lambda)
}

class GestureStateObservable(
        private val view: GestureView,
        private val lambda: (State) -> Unit
) : Observable<State>() {

    override fun subscribeActual(observer: Observer<in State>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, lambda, observer)
        observer.onSubscribe(listener)
        view.controller.addOnStateChangeListener(listener)
    }

    private class Listener(
            private val view: GestureView,
            private val lambda: (State) -> Unit,
            private val observer: Observer<in State>
    ) : MainThreadDisposable(), GestureController.OnStateChangeListener {

        override fun onStateReset(oldState: State, newState: State) {
            if (!isDisposed) {
                try {
                    lambda(newState)
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
                    lambda(state)
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

fun checkMainThread(observer: Observer<*>): Boolean {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        observer.onSubscribe(Disposables.empty())
        observer.onError(IllegalStateException(
                "Expected to be called on the main thread but was " + Thread.currentThread().name))
        return false
    }

    return true
}