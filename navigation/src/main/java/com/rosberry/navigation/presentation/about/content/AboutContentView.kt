package com.rosberry.navigation.presentation.about.content

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rosberry.navigation.ui.about.AboutItem

/**
 * @author mmikhailov on 01.10.2018.
 */
interface AboutContentView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setAboutItems(items: List<AboutItem>)
}