package com.rosberry.sample.surfaceviewrxed.di.main

import com.rosberry.sample.surfaceviewrxed.presentation.main.MainPresenter
import com.rosberry.sample.surfaceviewrxed.ui.main.MainActivity
import dagger.Subcomponent

/**
 * @author mmikhailov on 28/03/2019.
 */
@MainScope
@Subcomponent(modules = [MySceneModule::class])
interface MainComponent {

    fun inject(target: MainActivity)
    fun providePresenter(): MainPresenter
}