package com.rosberry.android.sample.presentation.post.add

import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.main.list.PostItem

interface AddPostView : MvpView {

    companion object {
        const val TAG = "add_post_view"
    }

    fun showPostDetails(post: Post);

    fun finish()
}