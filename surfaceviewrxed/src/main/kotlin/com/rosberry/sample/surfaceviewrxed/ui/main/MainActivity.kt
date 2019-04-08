package com.rosberry.sample.surfaceviewrxed.ui.main

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.sample.surfaceviewrxed.R
import com.rosberry.sample.surfaceviewrxed.di.Injector
import com.rosberry.sample.surfaceviewrxed.di.main.MainSceneQualifier
import com.rosberry.sample.surfaceviewrxed.presentation.main.MainPresenter
import com.rosberry.sample.surfaceviewrxed.presentation.main.MainView
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.MySceneData
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import com.rosberry.sample.surfaceviewrxed.ui.main.system.states
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @field:MainSceneQualifier
    lateinit var canvasHandler: CanvasHandler

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = Injector.mainComponent!!.providePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.openMainScope(MySceneData.sceneParams)
            .inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        surfaceView.setCanvasHandler(canvasHandler)
        surfaceView.setSceneParams(MySceneData.sceneParams) // todo исправить повторение
    }

    override fun registerSurfaceStates() {
        presenter.setSurfaceStatesObs(surfaceView.states())
    }

    override fun closeScope() {
        Injector.closeMainScope()
    }
}
