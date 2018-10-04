package com.rosberry.navigation.ui.profile.details

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.profile.details.ProfileDetailsPresenter
import com.rosberry.navigation.presentation.profile.details.ProfileDetailsView
import com.rosberry.navigation.ui.base.BaseFragment

/**
 * @author mmikhailov on 28.09.2018.
 */
class ProfileDetailsFragment : BaseFragment(), ProfileDetailsView {

    companion object {
        fun newInstance() = ProfileDetailsFragment()
    }

    override val layoutRes = R.layout.f_profile_details

    @InjectPresenter
    lateinit var presenter: ProfileDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): ProfileDetailsPresenter {
        return AndroidInjector
            .openProfileScope()
            .plusProfileDetailsComponent()
            .providePresenter()
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}