package com.rosberry.android.sample.presentation.post.edit

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.post.add.AddPostInteractor
import com.rosberry.android.sample.presentation.base.BasePresenter
import com.rosberry.android.sample.system.LogUtil
import javax.inject.Inject

@InjectViewState
class EditPostPresenter @Inject constructor(
        viewData: EditPostViewData,
        val addPostInteractor: AddPostInteractor
) : BasePresenter<EditPostView, EditPostViewData>(viewData) {

    override fun onFirstViewAttach() {

    }

    fun changePostTitle(text: String) {
        addPostInteractor.changeTitle(text)
    }

    fun changePostDescription(text: String) {
        addPostInteractor.changeDescription(text)
    }

}