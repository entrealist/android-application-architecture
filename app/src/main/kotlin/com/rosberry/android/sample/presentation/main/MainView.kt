package com.rosberry.android.sample.presentation.main

import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun setTitle(text: String)
}