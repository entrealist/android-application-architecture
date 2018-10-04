package com.rosberry.navigation.di.settings

import com.rosberry.navigation.presentation.settings.SettingsPresenter
import com.rosberry.navigation.ui.settings.SettingsActivity
import dagger.Subcomponent

/**
 * @author mmikhailov on 30.09.2018.
 */
@SettingsScope
@Subcomponent
interface SettingsComponent {

    fun providePresenter(): SettingsPresenter
    fun inject(activity: SettingsActivity)
}