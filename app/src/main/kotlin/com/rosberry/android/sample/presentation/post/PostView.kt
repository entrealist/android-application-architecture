package com.rosberry.android.sample.presentation.post

import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.entity.Post

interface PostView : MvpView {

    companion object {
        const val TAG = "post_view"
    }

    fun setPostDetails(title: String, description: String)
    fun showAddPost(post: Post)

    fun close()
}