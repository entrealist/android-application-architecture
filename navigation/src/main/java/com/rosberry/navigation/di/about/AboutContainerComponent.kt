package com.rosberry.navigation.di.about

import com.rosberry.navigation.presentation.about.AboutContainerPresenter
import com.rosberry.navigation.ui.about.AboutContainerFragment
import dagger.Subcomponent

/**
 * @author mmikhailov on 30.09.2018.
 */
@AboutScope
@Subcomponent(modules = [NavigationModule::class])
interface AboutContainerComponent {

    fun providePresenter(): AboutContainerPresenter
    fun inject(fragment: AboutContainerFragment)

    fun plusAboutContentComponent(): AboutContentComponent
}