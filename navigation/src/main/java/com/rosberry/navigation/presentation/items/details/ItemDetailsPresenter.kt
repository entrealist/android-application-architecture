package com.rosberry.navigation.presentation.items.details

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.di.items.details.ItemsItemIdQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import javax.inject.Inject

/**
 * @author mmikhailov on 30.09.2018.
 */
@InjectViewState
class ItemDetailsPresenter @Inject constructor(
        @ItemsItemIdQualifier private val itemId: String
) : BasePresenter<ItemDetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setItemDetails(itemId)
    }
}