package com.rosberry.android.sample.ui.post

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.PostDetailsPresenter
import com.rosberry.android.sample.presentation.post.PostView
import com.rosberry.android.sample.ui.base.BaseFragment
import kotlinx.android.synthetic.main.f_post.*

class PostDetailsFragment : BaseFragment(), PostView {

    companion object {
        const val ARG_USER_POST = "user_post"

        fun newInstance(post: Post) = PostDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER_POST, post)
            }
        }
    }

    @InjectPresenter
    lateinit var presenter: PostDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): PostDetailsPresenter {
        val userPost = arguments?.getSerializable(PostDetailsFragment.ARG_USER_POST) as Post
        return AndroidInjector.openPostScope(userPost)
            .getPostPresenter()
    }

    init {
        fragmentModel.layoutId = R.layout.f_post
        fragmentModel.initDialog(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogNoTitle, true)
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
    }

    override fun setPostDetails(title: String, description: String) {
        textTitle.text = title
        textDescription.text = description
    }

    override fun close() {
        dismiss()
    }
}