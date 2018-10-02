package com.rosberry.android.sample.ui.post.add

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.presentation.post.add.AddPostPresenter
import com.rosberry.android.sample.presentation.post.add.AddPostView
import com.rosberry.android.sample.ui.base.BaseActivity
import com.rosberry.android.sample.ui.post.PostDetailsFragment
import com.rosberry.android.sample.ui.post.edit.EditPostFragment

class AddPostActivity : BaseActivity(), AddPostView {

    @InjectPresenter
    lateinit var presenter: AddPostPresenter

    @ProvidePresenter
    fun providePresenter(): AddPostPresenter {
        val post = intent.getSerializableExtra(PostDetailsFragment.ARG_USER_POST) as Post
        return AndroidInjector.openAddPostScope(post)
            .getAddPostPresenter()
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.frameworkAdapter.saveInstanceState(outState)
    }

    override fun showPostDetails(post: Post) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentView1, PostDetailsFragment.newInstance(post, false))
            .commit()
    }

    override fun showEditPost() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentView2, EditPostFragment.newInstance())
            .commit()
    }
}
