package com.rosberry.android.sample.ui.post.add

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.add.AddPostPresenter
import com.rosberry.android.sample.presentation.post.add.AddPostView
import com.rosberry.android.sample.presentation.post.detail.PostDetailsView
import com.rosberry.android.sample.presentation.post.edit.EditPostView
import com.rosberry.android.sample.ui.base.BaseActivity
import com.rosberry.android.sample.ui.base.model.DialogModel
import com.rosberry.android.sample.ui.post.PostDetailsFragment
import com.rosberry.android.sample.ui.post.edit.EditPostFragment
import kotlinx.android.synthetic.main.a_add_post.*
import org.jetbrains.anko.toast

class AddPostActivity : BaseActivity(), AddPostView {

    private var progressDialog: MaterialDialog? = null

    @InjectPresenter
    lateinit var presenter: AddPostPresenter

    @ProvidePresenter
    fun providePresenter(): AddPostPresenter {
        val post = intent.getSerializableExtra(PostDetailsFragment.ARG_USER_POST) as Post
        return AndroidInjector.openAddPostScope(post)
            .provideAddPostPresenter()
    }

    init {
        model.backId = R.drawable.md_nav_back
        model.layoutId = R.layout.a_add_post
        model.title = R.string.add_post
        model.toolbarId = R.id.toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.frameworkAdapter.restoreInstanceState(this, savedInstanceState)
        buttonSend.setOnClickListener { presenter.clickSendPost() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.frameworkAdapter.saveInstanceState(outState)
    }

    override fun setSendButtonEnabled(enabled: Boolean) {
        buttonSend.isEnabled = enabled
    }

    override fun showPostDetails(post: Post) {
        if (supportFragmentManager.findFragmentByTag(EditPostView.TAG) == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentView1, PostDetailsFragment.newInstance(post, false),
                        PostDetailsView.TAG)
                .commit()
    }

    override fun showEditPost() {
        if (supportFragmentManager.findFragmentByTag(EditPostView.TAG) == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentView2, EditPostFragment.newInstance(), EditPostView.TAG)
                .commit()
    }

    override fun showToast(model: DialogModel) {
        toast(model.content)
    }


    override fun showProgress(visible: Boolean) {
        progressDialog?.dismiss()
        if (visible) {
            progressDialog = MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.please_wait)
                .build()

            progressDialog?.show()
        }

    }

    override fun finish() {
        super.finish()
    }

}
