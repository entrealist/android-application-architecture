package com.rosberry.sample.surfaceviewrxed.presentation.main

import android.graphics.Color
import com.alexvasilkov.gestures.State
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rosberry.sample.surfaceviewrxed.di.main.MainSceneQualifier
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background.BackgroundState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid.GridState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.StateObserver
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author mmikhailov on 28/03/2019.
 */
@InjectViewState
class MainPresenter @Inject constructor(
        @MainSceneQualifier
        private val mySceneStateObserver: StateObserver
) : MvpPresenter<MainView>() {

    private var viewDisposables = CompositeDisposable()

    override fun attachView(view: MainView?) {
        super.attachView(view)

        viewState.registerSurfaceStates()
    }

    override fun detachView(view: MainView?) {
        super.detachView(view)

        viewDisposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewState.closeScope()
    }

    fun setSurfaceStatesObs(states: Observable<State>) {
        states
            .map { GridState().apply { set(it) } }
            .flatMap { Observable.just(calculateBackgroundState(it.zoom), it) }
            .subscribe { mySceneStateObserver.pushState(it) }
            .connect()
    }

    private fun calculateBackgroundState(zoom: Float): BackgroundState {
        val red = if (zoom < 1.0f)
            (255 * zoom).toInt()
        else
            255

        return BackgroundState()
            .apply { backgroundColor = Color.rgb(red, 255, 255) }
    }

    private fun Disposable.connect() {
        viewDisposables.add(this)
    }
}