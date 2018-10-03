package com.rosberry.android.sample.presentation.post.edit

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.post.add.AddPostInteractor
import com.rosberry.android.sample.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class EditPostPresenter @Inject constructor(
        viewData: EditPostViewData,
        val addPostInteractor: AddPostInteractor
) : BasePresenter<EditPostView, EditPostViewData>(viewData) {

    override fun onFirstViewAttach() {
        addPostInteractor
            .listenContextId()
            .subscribe { viewState.showContextId(it) }
            .connect()
    }

    fun changePostTitle(text: String) {
        addPostInteractor.changeTitle(text)
    }

    fun changePostDescription(text: String) {
        addPostInteractor.changeDescription(text)
    }

}