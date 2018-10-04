package com.rosberry.navigation.di.profile.notifications

import com.rosberry.navigation.presentation.profile.notifications.ProfileNotificationsPresenter
import dagger.Subcomponent

/**
 * @author mmikhailov on 28.09.2018.
 */
@Subcomponent
interface ProfileNotificationsComponent {

    fun providePresenter(): ProfileNotificationsPresenter
}