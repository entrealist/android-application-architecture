package com.rosberry.sample.surfaceviewrxed.presentation.system.drawing

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author mmikhailov on 29/03/2019.
 */
abstract class StateObserver {

    private val stateSubject = PublishSubject.create<LayerState>()

    protected val states: Observable<LayerState> = stateSubject
        .doOnNext { acceptState(it) }

    abstract fun acceptState(state: LayerState)

    fun pushState(state: LayerState) {
        stateSubject.onNext(state)
    }
}