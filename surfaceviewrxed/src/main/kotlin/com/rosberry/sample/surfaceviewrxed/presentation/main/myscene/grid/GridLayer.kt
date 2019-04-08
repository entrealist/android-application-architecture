package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.grid

import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.Layer

/**
 * @author mmikhailov on 30/03/2019.
 */
class GridLayer(
        boardWidth: Float,
        boardHeight: Float
): Layer<GridState>() {

    init {
        setRenderables(GridRenderable(), NumbersRenderable())
    }

    override val state = GridState(boardWidth, boardHeight)

    override fun onStateChange(updated: GridState) {

    }
}
