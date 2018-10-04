package com.rosberry.navigation.ui.profile.notifications

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.profile.notifications.ProfileNotificationsPresenter
import com.rosberry.navigation.presentation.profile.notifications.ProfileNotificationsView
import com.rosberry.navigation.ui.base.BaseFragment

/**
 * @author mmikhailov on 28.09.2018.
 */
class ProfileNotificationsFragment : BaseFragment(), ProfileNotificationsView {

    companion object {
        fun newInstance() = ProfileNotificationsFragment()
    }

    override val layoutRes = R.layout.f_profile_notifications

    @InjectPresenter
    lateinit var presenter: ProfileNotificationsPresenter

    @ProvidePresenter
    fun providePresenter(): ProfileNotificationsPresenter {
        return AndroidInjector
            .openProfileScope()
            .plusProfileNotificationsComponent()
            .providePresenter()
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}