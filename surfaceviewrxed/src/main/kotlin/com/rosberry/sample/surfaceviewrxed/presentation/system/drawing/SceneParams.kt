package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

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
        var backgroundColorDark: String,
        var backgroundColorLight: String,
        var gridParams: GridParams
)

data class GridParams(
        var width: Float,
        var height: Float,
        var color: String,
        var thick: Float,
        var textSize: Float,
        var textColor: String
)