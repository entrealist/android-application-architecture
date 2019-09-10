package com.rosberry.navigation.presentation.about

import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.about.AboutNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
@InjectViewState
class AboutContainerPresenter @Inject constructor(
        @AboutNavigationQualifier private val aboutRouter: Router
) : BasePresenter<AboutContainerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        aboutRouter.replaceScreen(Screens.AboutContentScreen())
    }

    fun pressBack() {
        aboutRouter.exit()
    }
}