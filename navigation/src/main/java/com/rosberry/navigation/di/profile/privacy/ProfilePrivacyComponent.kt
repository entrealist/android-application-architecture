package com.rosberry.navigation.di.profile.privacy

import com.rosberry.navigation.presentation.profile.privacy.ProfilePrivacyPresenter
import dagger.Subcomponent

/**
 * @author mmikhailov on 28.09.2018.
 */
@Subcomponent
interface ProfilePrivacyComponent {

    fun providePresenter(): ProfilePrivacyPresenter
}