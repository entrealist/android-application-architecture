package com.rosberry.navigation.ui.profile

import android.content.Context
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.profile.ProfileNavigationQualifier
import com.rosberry.navigation.presentation.profile.ProfileContainerPresenter
import com.rosberry.navigation.ui.base.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


/**
 * @author mmikhailov on 27.09.2018.
 */
class ProfileContainerFragment : BaseFragment(), ProfileContainerView {

    companion object {
        fun newInstance(): ProfileContainerFragment {
            return ProfileContainerFragment()
        }
    }

    override val layoutRes = R.layout.f_profile_container

    private val navigator by lazy { SupportAppNavigator(activity, childFragmentManager, R.id.fragmentContainer) }

    @Inject
    @field:ProfileNavigationQualifier
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: ProfileContainerPresenter

    @ProvidePresenter
    fun providePresenter(): ProfileContainerPresenter {
        return AndroidInjector
            .openProfileScope()
            .providePresenter()
    }

    override fun onAttach(context: Context?) {
        AndroidInjector
            .openProfileScope()
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