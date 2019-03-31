package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground

import android.graphics.Canvas
import android.graphics.Color
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Renderable

class BackgroundRenderable : Renderable<GroundState> {

    override fun draw(canvas: Canvas, state: GroundState) {
        canvas.drawColor(Color.WHITE)
    }
}
