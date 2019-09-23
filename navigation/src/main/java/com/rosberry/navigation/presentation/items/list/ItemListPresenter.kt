package com.rosberry.navigation.presentation.items.list

import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.items.ItemsNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import moxy.MvpView
import ru.terrakok.cicerone.Router
import java.util.Random
import javax.inject.Inject

/**
 * @author mmikhailov on 30.09.2018.
 */
@InjectViewState
class ItemListPresenter @Inject constructor(
        @ItemsNavigationQualifier private val itemsRouter: Router
) : BasePresenter<MvpView>() {

    fun clickOnButton() {
        openItemDetailsScreen(Random().nextInt().toString())
    }

    private fun openItemDetailsScreen(id: String) {
        itemsRouter.navigateTo(Screens.ItemDetailsScreen(id))
    }
}