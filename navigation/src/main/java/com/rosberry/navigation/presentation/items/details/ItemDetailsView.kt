package com.rosberry.navigation.presentation.items.details

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author mmikhailov on 30.09.2018.
 */
interface ItemDetailsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setItemDetails(id: String)
}