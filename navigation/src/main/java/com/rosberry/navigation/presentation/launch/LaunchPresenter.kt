package com.rosberry.navigation.presentation.launch

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
@InjectViewState
class LaunchPresenter @Inject constructor(
        @GlobalNavigationQualifier private val router: Router
) : BasePresenter<LaunchView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val openLogin = true
        val openOnBoarding = true
        when {
            openLogin -> router.newRootScreen(Screens.LoginScreen())
            openOnBoarding -> router.newRootScreen(Screens.OnBoardingScreen())
            else -> router.newRootScreen(Screens.BottomNavigationScreen())
        }
    }

    fun pressBack() {
        router.exit()
    }
}