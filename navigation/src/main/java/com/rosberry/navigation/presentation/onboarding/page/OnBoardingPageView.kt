package com.rosberry.navigation.presentation.onboarding.page

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author mmikhailov on 01.10.2018.
 */
interface OnBoardingPageView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun completeOnBoarding()
}