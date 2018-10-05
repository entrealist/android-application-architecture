package com.rosberry.navigation.ui.about

import android.content.Context
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.about.AboutNavigationQualifier
import com.rosberry.navigation.presentation.about.AboutContainerPresenter
import com.rosberry.navigation.presentation.about.AboutContainerView
import com.rosberry.navigation.ui.base.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
class AboutContainerFragment : BaseFragment(), AboutContainerView {

    companion object {
        fun newInstance(): AboutContainerFragment {
            return AboutContainerFragment()
        }
    }

    override val layoutRes = R.layout.f_about_container

    private val navigator by lazy { SupportAppNavigator(activity, childFragmentManager, R.id.fragmentContainer) }

    @Inject
    @field:AboutNavigationQualifier
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: AboutContainerPresenter

    @ProvidePresenter
    fun providePresenter(): AboutContainerPresenter {
        return AndroidInjector
            .openAboutScope()
            .providePresenter()
    }

    override fun onAttach(context: Context?) {
        AndroidInjector
            .openAboutScope()
            .inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        presenter.pressBack()
        return true
    }
}