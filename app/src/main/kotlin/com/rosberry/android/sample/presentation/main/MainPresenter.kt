package com.rosberry.android.sample.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.main.MainInteractor
import com.rosberry.android.sample.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(viewData: MainViewData, val mainInteractor: MainInteractor)
    : BasePresenter<MainView, MainViewData>(viewData) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainInteractor.getPosts().subscribe()
    }
}