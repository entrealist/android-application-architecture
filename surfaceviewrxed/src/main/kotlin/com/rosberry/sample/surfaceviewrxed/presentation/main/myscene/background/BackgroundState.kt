package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import android.graphics.Color
import android.support.annotation.ColorInt
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class BackgroundState: LayerState {

    @get:ColorInt
    val backgroundColor: Int
        get() = calculateBackground()

    var maxZoom = 0f
    var minZoom = 0f
    var curZoom = 1f

    override fun update(other: LayerState) {
        if (other is BackgroundState) {
            this.curZoom = other.curZoom
            this.maxZoom = other.maxZoom
            this.minZoom = other.minZoom
        }
    }

    private fun calculateBackground(): Int {
        var red = 255
        var green = 255
        var blue = 255

        when {
            curZoom < minZoom -> {
                val coefficient = 1 - (minZoom - curZoom)
                red = (red * coefficient).toInt()
                blue = (blue * coefficient).toInt()
            }
            curZoom > maxZoom -> {
                val coefficient = maxZoom / curZoom
                green = (green * coefficient).toInt()
                blue = (blue * coefficient).toInt()
            }
        }

        return Color.rgb(red, green, blue)
    }
}