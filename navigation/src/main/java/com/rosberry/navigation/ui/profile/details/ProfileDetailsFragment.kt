package com.rosberry.navigation.ui.profile.details

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.profile.details.ProfileDetailsPresenter
import com.rosberry.navigation.presentation.profile.details.ProfileDetailsView
import com.rosberry.navigation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.f_profile_details.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toPrivacyBtn.setOnClickListener { presenter.clickToPrivacy() }
        toNotificationsBtn.setOnClickListener { presenter.clickToNotifications() }
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}