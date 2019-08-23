package com.rosberry.sample.surfaceviewrxed.di.main

import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.MySceneComposer
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.StateObserver
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import dagger.Module
import dagger.Provides

/**
 * @author mmikhailov on 28/03/2019.
 */
@Module
class MySceneModule {

    @MainScope
    @Provides
    @MainSceneQualifier
    fun provideMySceneRenderer(): CanvasHandler {
        return MySceneComposer()
    }

    @MainScope
    @Provides
    @MainSceneQualifier
    fun provideMySceneModelHolder(@MainSceneQualifier canvasHandler: CanvasHandler): StateObserver {
        return canvasHandler as StateObserver
    }
}