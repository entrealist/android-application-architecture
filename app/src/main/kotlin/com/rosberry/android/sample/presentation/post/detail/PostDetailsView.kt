package com.rosberry.android.sample.presentation.post.detail

import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.entity.Post

interface PostDetailsView : MvpView {

    fun setPostDetails(title: String, description: String)
    fun showAddPost(post: Post)

    fun close()
}