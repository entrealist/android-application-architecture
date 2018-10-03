package com.rosberry.android.sample.presentation.post.add

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.post.add.AddPostInteractor
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.base.BasePresenter
import com.rosberry.android.sample.ui.base.model.DialogModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

        restoreContextIdForPost()
    }

    private fun restoreContextIdForPost() {
        if (viewData.contextId == 0)
            addPostInteractor
                .generateContextId()
                .subscribe {
                    viewData.contextId = it
                    addPostInteractor.changeContextId(viewData.contextId)
                }
                .connect()
        else {
            addPostInteractor.changeContextId(viewData.contextId)
        }
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
        sendPost()
    }

    private fun sendPost() {
        addPostInteractor.sendPost(viewData.post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress(true) }
            .doOnEvent { _, _ -> viewState.showProgress(false) }
            .subscribe(
                    { viewState.finish() },
                    {
                        viewState.showToast(DialogModel.Builder()
                            .content(it.message.toString())
                            .build())
                    })
            .connect()

    }

}