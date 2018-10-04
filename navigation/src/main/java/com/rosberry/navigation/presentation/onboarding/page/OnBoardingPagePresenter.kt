package com.rosberry.navigation.presentation.onboarding.page

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.di.onboarding.OnBoardingNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
@InjectViewState
class OnBoardingPagePresenter @Inject constructor(
        @OnBoardingNavigationQualifier private val onBoardingRouter: Router
) : BasePresenter<OnBoardingPageView>() {

    fun clickOnStart() {
        viewState.completeOnBoarding()
    }

    fun pressBack(): Boolean {
        return false
    }
}