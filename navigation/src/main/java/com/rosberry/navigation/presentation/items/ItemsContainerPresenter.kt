package com.rosberry.navigation.presentation.items

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.items.ItemsNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 27.09.2018.
 */
@InjectViewState
class ItemsContainerPresenter @Inject constructor(
        @ItemsNavigationQualifier private val itemsRouter: Router
) : BasePresenter<ItemsContainerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        itemsRouter.replaceScreen(Screens.ItemListScreen())
    }

    fun pressBack() {
        itemsRouter.exit()
    }
}