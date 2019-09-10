package com.rosberry.navigation.presentation.login

import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
@InjectViewState
class LoginPresenter @Inject constructor(
        @GlobalNavigationQualifier private val router: Router
) : BasePresenter<LoginView>() {

    fun clickLogIn(showOnBoarding: Boolean) {
        if (showOnBoarding) {
            router.newRootScreen(Screens.OnBoardingScreen())
        } else {
            router.newRootScreen(Screens.BottomNavigationScreen())
        }
    }

    fun pressBack() {
        router.finishChain()
    }
}