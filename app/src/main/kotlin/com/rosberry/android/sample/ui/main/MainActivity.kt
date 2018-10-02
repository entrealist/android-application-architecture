package com.rosberry.android.sample.ui.main

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.main.MainPresenter
import com.rosberry.android.sample.presentation.main.MainView
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.presentation.post.detail.PostDetailsView
import com.rosberry.android.sample.system.gone
import com.rosberry.android.sample.system.show
import com.rosberry.android.sample.ui.base.BaseActivity
import com.rosberry.android.sample.ui.base.model.DialogModel
import com.rosberry.android.sample.ui.main.list.PostsAdapter
import com.rosberry.android.sample.ui.post.PostDetailsFragment
import kotlinx.android.synthetic.main.a_main.*

class MainActivity : BaseActivity(), MainView, PostsAdapter.OnItemClickListener {

    val postsAdapter = PostsAdapter(this)

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return AndroidInjector.openMainScope()
            .getMainPresenter()
    }

    init {
        model.layoutId = R.layout.a_main
        model.menuId = R.menu.m_main
        model.title = R.string.app_name
        model.toolbarId = R.id.toolbar
    }

    override fun onPostClicked(postItem: PostItem, pos: Int) {
        presenter.clickPost(postItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_action -> {
                // do something
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.frameworkAdapter.restoreInstanceState(this, savedInstanceState)
        recyclerPosts.adapter = postsAdapter
        buttonRetry.setOnClickListener { presenter.clickRetryLoadPosts() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.frameworkAdapter.saveInstanceState(outState)
    }

    override fun setTitle(text: String) {
        supportActionBar?.title = text
    }

    override fun setPosts(posts: List<PostItem>) = postsAdapter.submitList(posts)

    override fun showProgress(visible: Boolean) =
            if (visible) progressContent.show() else progressContent.hide()

    override fun showRetry(visible: Boolean) =
            if (visible) buttonRetry.show() else buttonRetry.gone()

    override fun showToast(toastModel: DialogModel) {}

    override fun showPostDetails(post: Post) {
        PostDetailsFragment.newInstance(post, true)
            .show(supportFragmentManager, PostDetailsView.TAG)
    }

}
