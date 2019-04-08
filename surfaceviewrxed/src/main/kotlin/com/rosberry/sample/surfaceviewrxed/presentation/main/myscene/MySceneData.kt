package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams

/**
 * @author mmikhailov on 08/04/2019.
 */
object MySceneData {

    const val MIN_ZOOM = 0.5f
    const val MAX_ZOOM = 6.0f
    private const val OVERZOOM_FACTOR = 2.0f
    private const val GESTURE_ANIMATION_DURATION = 150L

    val sceneParams = SceneParams(5000f, 5000f, MAX_ZOOM, MIN_ZOOM, OVERZOOM_FACTOR, GESTURE_ANIMATION_DURATION)
}