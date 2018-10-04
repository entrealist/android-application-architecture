package com.rosberry.navigation.di.onboarding.page

import com.rosberry.navigation.presentation.onboarding.page.OnBoardingPagePresenter
import dagger.Subcomponent

/**
 * @author mmikhailov on 01.10.2018.
 */
@Subcomponent
interface OnBoardingPageComponent {

    fun providePresenter(): OnBoardingPagePresenter
}