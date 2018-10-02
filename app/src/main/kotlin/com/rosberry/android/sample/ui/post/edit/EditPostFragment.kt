package com.rosberry.android.sample.ui.post.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.presentation.post.edit.EditPostPresenter
import com.rosberry.android.sample.presentation.post.edit.EditPostView
import com.rosberry.android.sample.ui.base.BaseFragment
import kotlinx.android.synthetic.main.f_edit_post.*

class EditPostFragment : BaseFragment(), EditPostView {

    companion object {

        fun newInstance() = EditPostFragment()
    }

    @InjectPresenter
    lateinit var presenter: EditPostPresenter

    @ProvidePresenter
    fun providePresenter(): EditPostPresenter {
        return AndroidInjector.openEditPostScope()
            .provideEditPostPresenter()
    }

    init {
        fragmentModel.layoutId = R.layout.f_edit_post
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.frameworkAdapter.restoreInstanceState(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.frameworkAdapter.saveInstanceState(outState)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxTextView.textChanges(editTitle)
            .subscribe { presenter.changePostTitle(it?.toString() ?: "") }
        RxTextView.textChanges(editDescription)
            .subscribe { presenter.changePostDescription(it?.toString() ?: "") }
    }

    override fun close() {
        dismiss()
    }
}