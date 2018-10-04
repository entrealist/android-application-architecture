package com.rosberry.navigation.di.main

import com.rosberry.navigation.di.about.AboutContainerComponent
import com.rosberry.navigation.di.items.ItemsContainerComponent
import com.rosberry.navigation.di.profile.ProfileContainerComponent
import com.rosberry.navigation.di.settings.SettingsComponent
import com.rosberry.navigation.presentation.main.BottomNavigationPresenter
import com.rosberry.navigation.ui.main.BottomNavigationActivity
import dagger.Subcomponent

/**
 * @author mmikhailov on 27.09.2018.
 */
@Subcomponent
interface BottomNavigationComponent {

    fun providePresenter(): BottomNavigationPresenter
    fun inject(activity: BottomNavigationActivity)

    fun plusProfileComponent(): ProfileContainerComponent
    fun plusItemsComponent(): ItemsContainerComponent
    fun plusAboutComponent(): AboutContainerComponent
    fun plusSettingsComponent(): SettingsComponent
}