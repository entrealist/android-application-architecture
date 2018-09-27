package com.rosberry.android.sample.presentation.main

import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.ui.base.model.DialogModel
import com.rosberry.android.sample.ui.main.list.PostsAdapter

interface MainView : MvpView, PostsAdapter.OnItemClickListener {
    fun setTitle(text: String)
    fun setPosts(posts: List<PostItem>)

    fun showProgress(visible: Boolean)
    fun showRetry(visible: Boolean)

    fun showToast(toastModel: DialogModel)
}