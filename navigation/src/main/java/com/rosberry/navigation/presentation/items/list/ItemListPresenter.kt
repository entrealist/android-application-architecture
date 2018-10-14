package com.rosberry.navigation.presentation.items.list

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.items.ItemsNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

/**
 * @author mmikhailov on 30.09.2018.
 */
@InjectViewState
class ItemListPresenter @Inject constructor(
        @ItemsNavigationQualifier private val itemsRouter: Router
) : BasePresenter<ItemListView>() {

    fun clickOnButton() {
        openItemDetailsScreen(Random().nextInt().toString())
    }

    private fun openItemDetailsScreen(id: String) {
        itemsRouter.navigateTo(Screens.ItemDetailsScreen(id))
    }
}