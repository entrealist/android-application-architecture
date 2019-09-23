package com.rosberry.sample.surfaceviewrxed.ui.main.system

import android.view.MotionEvent
import androidx.annotation.CheckResult
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.views.interfaces.GestureView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * @author mmikhailov on 10/04/2019.
 */
@CheckResult
fun GestureView.taps(): Observable<MotionEvent> {
    return GestureTapObservable(this)
}

class GestureTapObservable(
        private val view: GestureView
) : Observable<MotionEvent>() {

    override fun subscribeActual(observer: Observer<in MotionEvent>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(observer)
        observer.onSubscribe(listener)
        view.controller.setOnGesturesListener(listener)
    }

    private class Listener(
            private val observer: Observer<in MotionEvent>
    ) : MainThreadDisposable(), GestureController.OnGestureListener {

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            if (!isDisposed) {
                try {
                    observer.onNext(event)
                } catch (e: Exception) {
                    observer.onError(e)
                    dispose()
                }
            }

            return true
        }

        override fun onSingleTapUp(event: MotionEvent) = false
        override fun onDown(event: MotionEvent) {}
        override fun onDoubleTap(event: MotionEvent) = false
        override fun onUpOrCancel(event: MotionEvent) {}
        override fun onLongPress(event: MotionEvent) {}
        override fun onDispose() {}
    }
}