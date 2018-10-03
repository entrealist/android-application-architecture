package com.rosberry.android.sample.presentation.post.add

import com.arellomobile.mvp.MvpView
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.ui.base.model.DialogModel

interface AddPostView : MvpView {

    companion object {
        const val TAG = "add_post_view"
    }

    fun setSendButtonEnabled(enabled: Boolean)

    fun showProgress(visible: Boolean)
    fun showPostDetails(post: Post);
    fun showEditPost()
    fun showToast(model: DialogModel)

    fun finish()
}