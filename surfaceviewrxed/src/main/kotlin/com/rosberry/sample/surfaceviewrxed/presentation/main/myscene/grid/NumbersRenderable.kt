package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Renderable

/**
 * @author mmikhailov on 30/03/2019.
 */
class NumbersRenderable : Renderable<GridState> {

    private val numbersPaint = Paint(ANTI_ALIAS_FLAG)
    private var paintColor = 0
    private var textSize = 0f

    init {
        numbersPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas, state: GridState) {

        if (state.numbers.isEmpty()) return

        setPaint(state)

        val cellWidth = state.cellWidth
        val cellHeight = state.cellHeight
        val boardStartX = state.x
        val boardStartY = state.y

        val textWidth = numbersPaint.measureText("${state.columns}:${state.rows}")
        val diff = textWidth - cellWidth

        if (diff > 0) {
            numbersPaint.textSize = numbersPaint.textSize * (0.9f - diff / textWidth)
        }

        state.numbers.forEach {
                val text = it.first
                val column = it.second
                val row = it.third
                val cellX = boardStartX + cellWidth * column + cellWidth / 2
                val cellY = boardStartY + cellHeight * row + cellHeight / 2

                canvas.drawText(text, cellX, cellY, numbersPaint)
            }
    }

    private fun setPaint(state: GridState) {
        if (paintColor != state.textColor) {
            paintColor = state.textColor
            numbersPaint.color = state.textColor
        }

        if (textSize != state.textSize) {
            textSize = state.textSize
            numbersPaint.textSize = state.textSize
        }
    }
}
