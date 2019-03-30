package com.rosberry.sample.surfaceviewrxed.di.main

import com.rosberry.sample.surfaceviewrxed.presentation.main.MainPresenter
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
import com.rosberry.sample.surfaceviewrxed.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author mmikhailov on 28/03/2019.
 */
@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun sceneParams(sceneParams: SceneParams): Builder

        fun build(): MainComponent
    }

    fun inject(target: MainActivity)
    fun providePresenter(): MainPresenter
}