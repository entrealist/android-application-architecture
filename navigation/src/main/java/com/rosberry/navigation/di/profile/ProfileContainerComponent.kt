package com.rosberry.navigation.di.profile

import com.rosberry.navigation.di.profile.details.ProfileDetailsComponent
import com.rosberry.navigation.di.profile.notifications.ProfileNotificationsComponent
import com.rosberry.navigation.di.profile.privacy.ProfilePrivacyComponent
import com.rosberry.navigation.presentation.profile.ProfileContainerPresenter
import com.rosberry.navigation.ui.profile.ProfileContainerFragment
import dagger.Subcomponent

/**
 * @author mmikhailov on 27.09.2018.
 */
@ProfileScope
@Subcomponent(modules = [NavigationModule::class])
interface ProfileContainerComponent {

    fun providePresenter(): ProfileContainerPresenter
    fun inject(fragment: ProfileContainerFragment)

    fun plusProfileDetailsComponent(): ProfileDetailsComponent
    fun plusProfilePrivacyComponent(): ProfilePrivacyComponent
    fun plusProfileNotificationsComponent(): ProfileNotificationsComponent
}