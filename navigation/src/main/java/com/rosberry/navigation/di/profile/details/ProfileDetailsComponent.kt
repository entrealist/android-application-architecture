package com.rosberry.navigation.di.profile.details

import com.rosberry.navigation.presentation.profile.details.ProfileDetailsPresenter
import dagger.Subcomponent

/**
 * @author mmikhailov on 27.09.2018.
 */
@Subcomponent
interface ProfileDetailsComponent {

    fun providePresenter(): ProfileDetailsPresenter
}