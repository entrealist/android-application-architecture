package com.rosberry.navigation.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.di.main.TabsNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import com.rosberry.navigation.presentation.main.navigation.TabsRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 28.09.2018.
 */
@InjectViewState
class BottomNavigationPresenter @Inject constructor(
        @GlobalNavigationQualifier private val router: Router,
        @TabsNavigationQualifier private val tabsRouter: TabsRouter
) : BasePresenter<BottomNavigationView>() {

    fun clickBottomTab(position: Int) {
        val tab = when (position) {
            BottomNavigationView.Tabs.TAB_ABOUT_POSITION -> Screens.AboutContainerScreen()
            BottomNavigationView.Tabs.TAB_ITEMS_POSITION -> Screens.ItemsContainerScreen()
            BottomNavigationView.Tabs.TAB_PROFILE_POSITION -> Screens.ProfileContainerScreen()
            else -> null
        }

        tab?.let {
            viewState.selectTab(position, false)
            tabsRouter.openTab(tab)
        }
    }

    fun pressBack() {
        router.finishChain()
    }

    fun clickSettings() {
        router.navigateTo(Screens.SettingsScreen())
    }

    fun clickLogout() {
        eject()
    }

    private fun eject() {
        router.newRootScreen(Screens.LaunchScreen())
    }
}