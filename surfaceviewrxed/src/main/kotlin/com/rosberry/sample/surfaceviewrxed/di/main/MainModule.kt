package com.rosberry.sample.surfaceviewrxed.di.main

import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.MySceneComposer
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.StateHandler
import com.rosberry.sample.surfaceviewrxed.ui.main.system.CanvasHandler
import dagger.Module
import dagger.Provides

/**
 * @author mmikhailov on 28/03/2019.
 */
@Module
class MainModule {

    @MainScope
    @Provides
    @MainSceneQualifier
    fun provideMySceneRenderer(sceneParams: SceneParams): CanvasHandler {
        return MySceneComposer(sceneParams)
    }

    @MainScope
    @Provides
    @MainSceneQualifier
    fun provideMySceneModelHolder(@MainSceneQualifier canvasHandler: CanvasHandler): StateHandler {
        return canvasHandler as StateHandler
    }
}