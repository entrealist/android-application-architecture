package com.rosberry.android.sample.presentation.post.add

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.main.MainInteractor
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.base.BasePresenter

import javax.inject.Inject

@InjectViewState
class AddPostPresenter @Inject constructor(viewData: AddPostViewData)
    : BasePresenter<AddPostView, AddPostViewData>(viewData) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showPostDetails(viewData.post)
    }

}