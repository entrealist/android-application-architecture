package com.rosberry.navigation.presentation.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

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
}