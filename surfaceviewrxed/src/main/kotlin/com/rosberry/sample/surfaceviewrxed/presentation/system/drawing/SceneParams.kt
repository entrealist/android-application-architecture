package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

import android.support.annotation.ColorInt

/**
 * @author mmikhailov on 30/03/2019.
 */
data class SceneParams(
        var width: Float,
        var height: Float,
        var maxZoom: Float,
        var minZoom: Float,
        var overzoomFactor: Float,
        var gestureAnimationDuration: Long,
        var gridParams: GridParams
)

data class GridParams(
        var width: Float,
        var height: Float,
        @ColorInt
        var color: Int,
        var thick: Float
)