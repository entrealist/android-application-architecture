package com.rosberry.navigation.ui.about

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.about.content.AboutContentPresenter
import com.rosberry.navigation.presentation.about.content.AboutContentView
import com.rosberry.navigation.ui.base.BaseFragment

/**
 * @author mmikhailov on 01.10.2018.
 */
class AboutContentFragment : BaseFragment(), AboutContentView {

    companion object {
        fun newInstance(): AboutContentFragment {
            return AboutContentFragment()
        }
    }

    override val layoutRes = R.layout.f_about_content

    @InjectPresenter
    lateinit var presenter: AboutContentPresenter

    @ProvidePresenter
    fun providePresenter(): AboutContentPresenter {
        return AndroidInjector
            .openAboutScope()
            .plusAboutContentComponent()
            .providePresenter()
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}