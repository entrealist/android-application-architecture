package com.rosberry.navigation.presentation.profile

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.profile.ProfileNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import com.rosberry.navigation.ui.profile.ProfileContainerView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 27.09.2018.
 */
@InjectViewState
class ProfileContainerPresenter @Inject constructor(
        @ProfileNavigationQualifier private val localRouter: Router
) : BasePresenter<ProfileContainerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        localRouter.replaceScreen(Screens.ProfileDetailsScreen())
    }

    fun pressBack() {
        localRouter.exit()
    }
}
