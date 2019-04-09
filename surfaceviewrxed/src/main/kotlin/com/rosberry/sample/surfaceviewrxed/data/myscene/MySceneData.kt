package com.rosberry.sample.surfaceviewrxed.data.myscene

import android.graphics.Color
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.GridParams
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams

/**
 * @author mmikhailov on 08/04/2019.
 */
object MySceneData {

    private const val BOARD_WIDTH = 300f
    private const val BOARD_HEIGHT = 500f
    private const val MIN_ZOOM = 0.5f
    private const val MAX_ZOOM = 6.0f
    private const val OVERZOOM_FACTOR = 2.0f
    private const val GESTURE_ANIMATION_DURATION = 150L
    private const val GRID_CELL_SIZE_NOMINAL = 56f
    private const val GRID_COLOR = Color.RED
    private const val GRID_THICK = 2f

    private val gridParams = GridParams(
            GRID_CELL_SIZE_NOMINAL,
            GRID_COLOR,
            GRID_THICK
    )

    val sceneParams = SceneParams(
            BOARD_WIDTH,
            BOARD_HEIGHT,
            MAX_ZOOM,
            MIN_ZOOM,
            OVERZOOM_FACTOR,
            GESTURE_ANIMATION_DURATION,
            gridParams
    )
}