package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

/**
 * @author mmikhailov on 30/03/2019.
 */
interface LayerState {

    fun update(other: LayerState)
}