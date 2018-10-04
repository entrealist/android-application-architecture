package com.rosberry.navigation.ui.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rosberry.navigation.presentation.profile.PageTabModel

/**
 * @author mmikhailov on 27.09.2018.
 */
interface ProfileContainerView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initPagerAdapter(models: ArrayList<PageTabModel>)

}