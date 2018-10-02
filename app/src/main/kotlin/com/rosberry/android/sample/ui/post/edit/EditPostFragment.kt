package com.rosberry.android.sample.ui.post.edit

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.FrameLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.presentation.post.edit.EditPostPresenter
import com.rosberry.android.sample.presentation.post.edit.EditPostView
import com.rosberry.android.sample.system.show
import com.rosberry.android.sample.ui.base.BaseFragment
import com.rosberry.android.sample.ui.post.add.AddPostActivity
import kotlinx.android.synthetic.main.f_post.*

class EditPostFragment : BaseFragment(), EditPostView {

    companion object {

        fun newInstance() = EditPostFragment()
    }

    @InjectPresenter
    lateinit var presenter: EditPostPresenter

    @ProvidePresenter
    fun providePresenter(): EditPostPresenter {
        return AndroidInjector.openEditPostScope()
            .getEditPostPresenter()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun close() {
        dismiss()
    }
}