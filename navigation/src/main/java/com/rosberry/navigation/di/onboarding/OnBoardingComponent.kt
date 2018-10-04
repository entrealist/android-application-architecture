package com.rosberry.navigation.di.onboarding

import com.rosberry.navigation.di.onboarding.page.OnBoardingPageComponent
import com.rosberry.navigation.presentation.onboarding.OnBoardingPresenter
import com.rosberry.navigation.ui.onboarding.OnBoardingActivity
import dagger.Subcomponent

/**
 * @author mmikhailov on 01.10.2018.
 */
@OnBoardingScope
@Subcomponent(modules = [NavigationModule::class])
interface OnBoardingComponent {

    fun providePresenter(): OnBoardingPresenter
    fun inject(activity: OnBoardingActivity)

    fun plusOnBoardingPageComponent(): OnBoardingPageComponent
}