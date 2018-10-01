package com.rosberry.android.sample.presentation.post

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.presentation.base.BasePresenter
import com.rosberry.android.sample.system.LogUtil
import javax.inject.Inject

@InjectViewState
class PostDetailsPresenter @Inject constructor(viewData: PostViewData) :
        BasePresenter<PostView, PostViewData>(viewData) {

    override fun onFirstViewAttach() {
        LogUtil.d(this, "Post: " + viewData.post)
        with(viewData.post) {
            viewState.setPostDetails(title, description)
        }
    }
}