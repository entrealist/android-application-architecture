package com.rosberry.navigation.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

open class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    protected fun Disposable.connect(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    protected fun disposeAll() {
        compositeDisposable.dispose()
    }
}