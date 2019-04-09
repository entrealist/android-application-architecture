package com.rosberry.sample.surfaceviewrxed.presentation.main

import com.alexvasilkov.gestures.State
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rosberry.sample.surfaceviewrxed.R
import com.rosberry.sample.surfaceviewrxed.data.ResourceManager
import com.rosberry.sample.surfaceviewrxed.data.myscene.MySceneData
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
        private val mySceneStateObserver: StateObserver,
        private val resourceManager: ResourceManager
) : MvpPresenter<MainView>() {

    private val backgroundState = BackgroundState()
    private val gridState = GridState()

    private var viewDisposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        setupSceneParams()
    }

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
            .doOnNext { gridState.apply { set(it) } }
            .doOnNext { backgroundState.apply { curZoom = it.zoom } }
            .flatMap { Observable.just(backgroundState, gridState) }
            .subscribe { mySceneStateObserver.pushState(it) }
            .connect()
    }

    private fun setupSceneParams() {
        val sceneParams = MySceneData.sceneParams

        backgroundState.apply {
            maxZoom = sceneParams.maxZoom
            minZoom = sceneParams.minZoom
        }

        gridState.apply {
            boardWidth = sceneParams.width
            boardHeight = sceneParams.height
            gridCellSizeNominal = sceneParams.gridParams.gridCellSizeNominal
            gridColor = resourceManager.getColor(R.color.colorPrimaryDark)
            gridThick = sceneParams.gridParams.gridThick
        }

        viewState.setSceneParams(sceneParams)
    }

    private fun Disposable.connect() {
        viewDisposables.add(this)
    }
}