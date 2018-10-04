package com.rosberry.navigation.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author mmikhailov on 27.09.2018.
 */
interface BottomNavigationView : MvpView {

    object Tabs {
        const val TAB_ABOUT_POSITION = 0
        const val TAB_ITEMS_POSITION = 1
        const val TAB_PROFILE_POSITION = 2
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectTab(position: Int, shouldCallListener: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openTab(tabScreen: SupportAppScreen)
}