package com.rosberry.navigation.presentation.onboarding

import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.di.onboarding.OnBoardingNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
@InjectViewState
class OnBoardingPresenter @Inject constructor(
        @GlobalNavigationQualifier private val router: Router,
        @OnBoardingNavigationQualifier private val onBoardingRouter: Router
) : BasePresenter<OnBoardingView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        onBoardingRouter.newRootScreen(Screens.OnBoardingPage())
    }

    fun pressBack() {
        router.exit()
    }

    fun completeOnBoarding() {
        router.newRootScreen(Screens.BottomNavigationScreen())
    }
}