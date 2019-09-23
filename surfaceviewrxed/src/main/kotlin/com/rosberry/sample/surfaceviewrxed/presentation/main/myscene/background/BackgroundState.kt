package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import android.graphics.Color
import androidx.annotation.ColorInt
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState

/**
 * @author mmikhailov on 31/03/2019.
 */
class BackgroundState : LayerState {

    @ColorInt
    var backgroundColorBase = 0

    @get:ColorInt
    val backgroundColor: Int
        get() = calculateBackground()

    var maxZoom = 0f
    var minZoom = 0f
    var curZoom = 0f

    fun update(other: BackgroundState) {
        this.curZoom = other.curZoom
        this.maxZoom = other.maxZoom
        this.minZoom = other.minZoom
        this.backgroundColorBase = other.backgroundColorBase
    }

    private fun calculateBackground(): Int {
        var red = Color.red(backgroundColorBase)
        var green = Color.green(backgroundColorBase)
        var blue = Color.blue(backgroundColorBase)

        when {
            curZoom < minZoom -> {
                val coefficient = 1 - (minZoom - curZoom)
                green = (green * coefficient).toInt()
                blue = (blue * coefficient).toInt()
            }
            curZoom > maxZoom -> {
                val coefficient = maxZoom / curZoom
                red = (red * coefficient).toInt()
                blue = (blue * coefficient).toInt()
            }
        }

        return Color.rgb(red, green, blue)
    }
}