package com.rosberry.navigation.ui.profile.privacy

import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.profile.privacy.ProfilePrivacyPresenter
import com.rosberry.navigation.presentation.profile.privacy.ProfilePrivacyView
import com.rosberry.navigation.ui.base.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * @author mmikhailov on 28.09.2018.
 */
class ProfilePrivacyFragment : BaseFragment(), ProfilePrivacyView {

    companion object {
        fun newInstance() = ProfilePrivacyFragment()
    }

    override val layoutRes = R.layout.f_profile_privacy

    @InjectPresenter
    lateinit var presenter: ProfilePrivacyPresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePrivacyPresenter {
        return AndroidInjector
            .openProfileScope()
            .plusProfilePrivacyComponent()
            .providePresenter()
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}