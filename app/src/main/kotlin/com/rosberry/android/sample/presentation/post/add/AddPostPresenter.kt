package com.rosberry.android.sample.presentation.post.add

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.post.add.AddPostInteractor
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class AddPostPresenter @Inject constructor(
        viewData: AddPostViewData,
        val addPostInteractor: AddPostInteractor
) : BasePresenter<AddPostView, AddPostViewData>(viewData) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showPostDetails(viewData.post)
        viewState.showEditPost()
        if (viewData.newPost == null) viewData.newPost = Post(0, 0, "", "")
    }

    override fun attachView(view: AddPostView?) {
        super.attachView(view)

        addPostInteractor.listenTitleChange()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewData.newPost?.title = it
                viewState.setSendButtonEnabled(checkSendButtonEnabled())
            }
            .connect()

        addPostInteractor.listenDescriptionChange()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewData.newPost?.description = it
                viewState.setSendButtonEnabled(checkSendButtonEnabled())
            }
            .connect()
    }

    private fun checkSendButtonEnabled() = viewData.newPost != null
            && viewData.newPost?.title?.isNotEmpty()!!
            && viewData.newPost?.description?.isNotEmpty()!!


    fun clickSendPost() {
        //todo add post request
    }

}