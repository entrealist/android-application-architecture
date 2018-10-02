package com.rosberry.android.sample.ui.post

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.FrameLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.detail.PostDetailsPresenter
import com.rosberry.android.sample.presentation.post.detail.PostDetailsView
import com.rosberry.android.sample.system.show
import com.rosberry.android.sample.ui.base.BaseFragment
import com.rosberry.android.sample.ui.post.add.AddPostActivity
import kotlinx.android.synthetic.main.f_post.*

class PostDetailsFragment : BaseFragment(),
                            PostDetailsView {

    companion object {
        const val ARG_USER_POST = "user_post"
        const val ARG_IS_DIALOG = "is_dialog"

        fun newInstance(post: Post, isDialog: Boolean) = PostDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER_POST, post)
                putBoolean(ARG_IS_DIALOG, isDialog)
            }
        }
    }

    @InjectPresenter
    lateinit var presenter: PostDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): PostDetailsPresenter {
        val userPost = arguments?.getSerializable(PostDetailsFragment.ARG_USER_POST) as Post
        fragmentModel.isDialog = arguments?.getBoolean(PostDetailsFragment.ARG_IS_DIALOG,
                false) as Boolean

        return AndroidInjector.openPostDetailsScope(userPost)
            .providePostDetailsPresenter()
    }

    init {
        fragmentModel.layoutId = R.layout.f_post
        fragmentModel.setDialogStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogNoTitle, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.frameworkAdapter.restoreInstanceState(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.frameworkAdapter.saveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClose.setOnClickListener { close() }
        buttonReply.setOnClickListener { presenter.clickReply() }

        if (fragmentModel.isDialog) {
            buttonClose.show()
            buttonReply.show()
        } else {
            view.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
            view.layoutParams = view.layoutParams
        }
    }

    override fun setPostDetails(title: String, description: String) {
        textTitle.text = title
        textDescription.text = description
    }

    override fun showAddPost(post: Post) {

        view?.post {
            startActivity(Intent(context, AddPostActivity::class.java).apply {
                putExtra(PostDetailsFragment.ARG_USER_POST, post)
            })
        }
        dismiss()
    }

    override fun close() {
        dismiss()
    }
}