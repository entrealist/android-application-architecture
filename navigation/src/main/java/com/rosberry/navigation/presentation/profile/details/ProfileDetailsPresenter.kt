package com.rosberry.navigation.presentation.profile.details

import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.profile.ProfileNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 28.09.2018.
 */
@InjectViewState
class ProfileDetailsPresenter @Inject constructor(
        @ProfileNavigationQualifier private val router: Router
) : BasePresenter<ProfileDetailsView>() {

    fun clickToPrivacy() {
        router.navigateTo(Screens.ProfilePrivacyScreen())
    }

    fun clickToNotifications() {
        router.navigateTo(Screens.ProfileNotificationsScreen())
    }

}