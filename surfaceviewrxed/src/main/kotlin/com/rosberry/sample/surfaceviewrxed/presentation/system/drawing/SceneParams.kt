package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

import android.support.annotation.ColorInt

/**
 * @author mmikhailov on 30/03/2019.
 */
data class SceneParams(
        val width: Float,
        val height: Float,
        val maxZoom: Float,
        val minZoom: Float,
        val overzoomFactor: Float,
        val gestureAnimationDuration: Long,
        val gridParams: GridParams
)

data class GridParams(
        val gridCellSizeNominal: Float,
        @ColorInt
        val gridColor: Int,
        val gridThick: Float
)