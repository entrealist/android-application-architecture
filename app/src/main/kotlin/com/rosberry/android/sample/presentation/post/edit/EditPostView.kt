package com.rosberry.android.sample.presentation.post.edit

import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.entity.Post

interface EditPostView : MvpView {

    companion object {
        const val TAG = "edit_post_view"
    }

    fun close()
    fun showContextId(contextId: Int)
}