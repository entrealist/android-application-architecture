package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import android.graphics.Color
import android.support.annotation.ColorInt
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.Constant.MAX_ZOOM
import com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.Constant.MIN_ZOOM
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class BackgroundState : LayerState {

    @get:ColorInt
    val backgroundColor: Int
        get() = calculateBackground()

    var zoom = 1f

    private val maxZoom = MAX_ZOOM
    private val minZoom = MIN_ZOOM

    override fun update(other: LayerState) {
        if (other is BackgroundState) {
            this.zoom = other.zoom
        }
    }

    private fun calculateBackground(): Int {
        var red = 255
        var green = 255
        var blue = 255

        when {
            zoom < minZoom -> {
                val coefficient = 1 - (minZoom - zoom)
                red = (red * coefficient).toInt()
                blue = (blue * coefficient).toInt()
            }
            zoom > maxZoom -> {
                val coefficient = maxZoom / zoom
                green = (green * coefficient).toInt()
                blue = (blue * coefficient).toInt()
            }
        }

        return Color.rgb(red, green, blue)
    }
}