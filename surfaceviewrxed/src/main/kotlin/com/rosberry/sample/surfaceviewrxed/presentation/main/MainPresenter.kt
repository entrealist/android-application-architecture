package com.rosberry.sample.surfaceviewrxed.presentation.main

import android.graphics.PointF
import android.view.MotionEvent
import com.alexvasilkov.gestures.State
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.rosberry.sample.surfaceviewrxed.R
import com.rosberry.sample.surfaceviewrxed.data.ResourceManager
import com.rosberry.sample.surfaceviewrxed.data.myscene.MySceneData
import com.rosberry.sample.surfaceviewrxed.di.main.MainSceneQualifier
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background.BackgroundState
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid.GridState
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
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

    private val viewDisposables = CompositeDisposable()
    private val backgroundState = BackgroundState()
    private val gridState = GridState()

    private lateinit var sceneParams: SceneParams
    private lateinit var viewportCenter: PointF

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        setupSceneParams()
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)

        viewState.registerSurfaceEvents(
                { onSurfaceStatesObs(it) },
                { onSurfaceTapsObs(it) },
                { onViewportCenter(it) }
        )
    }

    override fun detachView(view: MainView?) {
        super.detachView(view)

        viewDisposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewState.closeScope()
    }

    private fun onViewportCenter(viewportCenter: PointF) {
        this.viewportCenter = viewportCenter
    }

    private fun onSurfaceStatesObs(states: Observable<State>) {
        states
            .doOnNext { gridState.apply { set(it) } }
            .doOnNext { backgroundState.apply { curZoom = it.zoom } }
            .flatMap { Observable.just(backgroundState, gridState) }
            .subscribe { mySceneStateObserver.pushState(it) }
            .connect()
    }

    private fun onSurfaceTapsObs(taps: Observable<MotionEvent>) {
        taps
            .subscribe { zoom(it.x, it.y) }
            .connect()
    }

    private fun setupSceneParams() {
        sceneParams = MySceneData.sceneParams

        backgroundState.apply {
            maxZoom = sceneParams.maxZoom
            minZoom = sceneParams.minZoom
        }

        gridState.apply {
            boardWidthNominal = sceneParams.width
            boardHeightNominal = sceneParams.height
            gridCellWidthNominal = sceneParams.gridParams.width
            gridCellHeightNominal = sceneParams.gridParams.height
            gridThickNominal = sceneParams.gridParams.thick
            gridColor = resourceManager.getColor(R.color.colorPrimaryDark)
        }

        viewState.setSceneParams(sceneParams)
    }

    private fun zoom(x: Float, y: Float) {
        gridState.zoomToCell(viewportCenter.x, viewportCenter.y, x, y , sceneParams.maxZoom)
        viewState.animateStateTo(gridState)
    }

    private fun Disposable.connect() {
        viewDisposables.add(this)
    }
}