package com.rosberry.android.sample.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.ui.base.model.DialogModel
import com.rosberry.android.sample.ui.main.list.PostsAdapter

interface MainView : MvpView {
    fun setTitle(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPosts(posts: List<PostItem>)

    fun showProgress(visible: Boolean)
    fun showRetry(visible: Boolean)

    fun showToast(toastModel: DialogModel)
}