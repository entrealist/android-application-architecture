package com.rosberry.android.sample.presentation.post.detail

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.presentation.base.BasePresenter
import com.rosberry.android.sample.system.LogUtil
import javax.inject.Inject

@InjectViewState
class PostDetailsPresenter @Inject constructor(viewData: PostDetailsViewData) :
        BasePresenter<PostDetailsView, PostDetailsViewData>(viewData) {

    override fun onFirstViewAttach() {
        LogUtil.d(this, "Post: " + viewData.post)
        with(viewData.post) {
            viewState.setPostDetails(title, description)
        }
    }

    fun clickReply() {
        viewState.showAddPost(viewData.post)
    }

}