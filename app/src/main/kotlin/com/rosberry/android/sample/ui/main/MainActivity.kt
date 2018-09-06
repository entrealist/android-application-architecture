package com.rosberry.android.sample.ui.main

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.android.sample.R
import com.rosberry.android.sample.domain.main.MainInteractor
import com.rosberry.android.sample.presentation.main.*
import com.rosberry.android.sample.ui.base.BaseActivity

class MainActivity : BaseActivity(), MainView{

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return MainPresenter(MainViewData(), MainInteractor())
    }

    init {
        model.backId = R.drawable.md_nav_back
        model.layoutId = R.layout.a_main
        model.menuId = R.menu.m_main
        model.title = R.string.app_name
        model.toolbarId = R.id.toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getAndroidAdapter().restoreInstanceState(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.getAndroidAdapter().saveInstanceState(outState)
    }

    override fun setTitle(text: String) {
        supportActionBar?.title = text
    }

}
