package com.rosberry.sample.surfaceviewrxed.data.myscene

import android.graphics.Color
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.GridParams
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.SceneParams

/**
 * @author mmikhailov on 08/04/2019.
 */
object MySceneData {

    private const val GRID_ROWS = 50
    private const val GRID_COLUMNS = 30
    private const val GRID_CELL_HEIGHT_NOMINAL = 80f
    private const val GRID_CELL_WIDTH_NOMINAL = GRID_CELL_HEIGHT_NOMINAL * 0.67f
    private const val BOARD_HEIGHT = GRID_CELL_HEIGHT_NOMINAL * GRID_ROWS
    private const val BOARD_WIDTH = GRID_CELL_WIDTH_NOMINAL * GRID_COLUMNS
    private const val GRID_COLOR = Color.RED
    private const val GRID_THICK = 2f
    private const val MIN_ZOOM = 1f
    private const val MAX_ZOOM = 4f
    private const val OVERZOOM_FACTOR = 2f
    private const val GESTURE_ANIMATION_DURATION = 150L
    private const val GRID_TEXT_SIZE_NOMINAL = GRID_CELL_WIDTH_NOMINAL
    private const val GRID_TEXT_COLOR = Color.BLACK

    private val gridParams = GridParams(
            GRID_CELL_WIDTH_NOMINAL,
            GRID_CELL_HEIGHT_NOMINAL,
            GRID_COLOR,
            GRID_THICK,
            GRID_TEXT_SIZE_NOMINAL,
            GRID_TEXT_COLOR
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