package com.rosberry.navigation.presentation.onboarding.page

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author mmikhailov on 01.10.2018.
 */
interface OnBoardingPageView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun completeOnBoarding()
}