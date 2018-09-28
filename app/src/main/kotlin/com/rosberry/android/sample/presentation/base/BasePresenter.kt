package com.rosberry.android.sample.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.data.persistence.internal.ViewDataRepository
import com.rosberry.android.sample.system.FrameworkAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V : MvpView, D : ViewDataRepository>(val viewData: D ) : MvpPresenter<V>() {

    val frameworkAdapter = FrameworkAdapter(viewData)

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