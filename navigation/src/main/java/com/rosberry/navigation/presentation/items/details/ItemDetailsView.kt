package com.rosberry.navigation.presentation.items.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author mmikhailov on 30.09.2018.
 */
interface ItemDetailsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setItemDetails(id: String)
}