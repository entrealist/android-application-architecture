package com.rosberry.navigation.presentation.profile.details

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.di.profile.ProfileNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 28.09.2018.
 */
@InjectViewState
class ProfileDetailsPresenter @Inject constructor(
        @ProfileNavigationQualifier private val router: Router
) : BasePresenter<ProfileDetailsView>() {

}