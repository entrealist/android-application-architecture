package com.rosberry.android.sample.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.rosberry.android.sample.domain.main.MainInteractor
import com.rosberry.android.sample.presentation.base.BasePresenter
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.system.LogUtil
import com.rosberry.android.sample.ui.base.model.DialogModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(viewData: MainViewData, val mainInteractor: MainInteractor)
    : BasePresenter<MainView, MainViewData>(viewData) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadPosts()
    }

    fun clickRetryLoadPosts() {
        loadPosts()
    }

    fun clickPost(postItem: PostItem) {
        LogUtil.d(this, "Post clicked: " + postItem.toString())
    }

    private fun loadPosts() {
        mainInteractor.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showRetry(false)
                viewState.showProgress(true)
            }
            .doOnEvent { _, _ -> viewState.showProgress(false) }
            .map { posts ->
                posts.also { viewData.posts = posts }
                    .map { PostItem(it.id, it.title, it.description) }
            }
            .subscribe({
                viewState.setPosts(it)
            }, {
                viewState.showToast(DialogModel.Builder()
                    .content(it.localizedMessage)
                    .build())
                viewState.showRetry(true)

            })
            .connect()
    }

}