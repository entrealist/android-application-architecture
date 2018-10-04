package com.rosberry.navigation.di.launch

import com.rosberry.navigation.presentation.launch.LaunchPresenter
import com.rosberry.navigation.ui.launch.LaunchActivity
import dagger.Subcomponent

/**
 * @author mmikhailov on 01.10.2018.
 */
@Subcomponent
interface LaunchComponent {

    fun providePresenter(): LaunchPresenter
    fun inject(activity: LaunchActivity)
}