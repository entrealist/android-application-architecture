package com.rosberry.sample.surfaceviewrxed.presentation.main

import android.view.MotionEvent
import com.alexvasilkov.gestures.State
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rosberry.sample.surfaceviewrxed.di.main.MainSceneQualifier
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground.GroundState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.middle.MiddleState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ui.UiState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.StateHandler
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author mmikhailov on 28/03/2019.
 */
@InjectViewState
class MainPresenter @Inject constructor(
        @MainSceneQualifier
        private val mySceneStateHandler: StateHandler
) : MvpPresenter<MainView>() {

    private var viewDisposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        //viewState.registerSurfaceTouches()
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
        states.map { GroundState().apply { set(it) } }
            .subscribe {  mySceneStateHandler.onState(it) }
            .let { viewDisposables.add(it) }
    }

    fun setSurfaceTouchesObs(touches: Observable<MotionEvent>) {
        touches.map { getState(it) }
            .subscribe { mySceneStateHandler.onState(it) }
            .let { viewDisposables.add(it) }
    }

    private fun getState(ev: MotionEvent): LayerState {
        return when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                GroundState()
            }
            MotionEvent.ACTION_UP -> {
                UiState()
            }

            else -> MiddleState()
        }
    }
}