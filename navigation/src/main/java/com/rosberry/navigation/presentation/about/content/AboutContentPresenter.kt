package com.rosberry.navigation.presentation.about.content

import com.rosberry.navigation.di.about.AboutNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 30.09.2018.
 */
@InjectViewState
class AboutContentPresenter @Inject constructor(
        @AboutNavigationQualifier private val aboutRouter: Router
) : BasePresenter<AboutContentView>() {

}