package com.rosberry.android.sample.presentation.post

import com.arellomobile.mvp.MvpView

interface PostView : MvpView {

    companion object {
        const val TAG = "post_view"
    }

    fun setPostDetails(title: String, description: String)

    fun close()
}