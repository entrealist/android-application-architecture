package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.background

import android.graphics.Canvas
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Renderable

class BackgroundRenderable: Renderable<BackgroundState> {

    override fun draw(canvas: Canvas, state: BackgroundState) {
        canvas.drawColor(state.backgroundColor)
    }
}
