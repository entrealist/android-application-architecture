package com.rosberry.sample.surfaceviewrxed.ui.main

import android.graphics.PointF
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import com.alexvasilkov.gestures.State
import com.rosberry.sample.surfaceviewrxed.R
import com.rosberry.sample.surfaceviewrxed.di.Injector
import com.rosberry.sample.surfaceviewrxed.di.main.MainSceneQualifier
import com.rosberry.sample.surfaceviewrxed.presentation.main.MainPresenter
import com.rosberry.sample.surfaceviewrxed.presentation.main.MainView
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import com.rosberry.sample.surfaceviewrxed.ui.main.system.alsoOnLaid
import com.rosberry.sample.surfaceviewrxed.ui.main.system.states
import com.rosberry.sample.surfaceviewrxed.ui.main.system.taps
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @field:MainSceneQualifier
    lateinit var canvasHandler: CanvasHandler

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.openMainScope()
            .inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        surfaceView.setCanvasHandler(canvasHandler)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.m_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.themeToggle) {
            presenter.clickToggleTheme()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun registerSurfaceEvents(statesDeck: (Observable<State>) -> Unit,
                                       tapsDeck: (Observable<MotionEvent>) -> Unit,
                                       viewportCenterDeck: (PointF) -> Unit) {

        statesDeck(surfaceView.states())
        tapsDeck(surfaceView.taps())

        surfaceView.alsoOnLaid {
            val centerPoint = PointF(surfaceView.width / 2f, surfaceView.height / 2f)
            viewportCenterDeck(centerPoint)
        }
    }

    override fun setSceneParams(sceneParams: SceneParams) {
        surfaceView.setSceneParams(sceneParams)
    }

    override fun animateStateTo(state: State) {
        surfaceView.animateStateTo(state)
    }

    override fun closeScope() {
        Injector.closeMainScope()
    }
}
