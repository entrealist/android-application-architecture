package com.rosberry.navigation.presentation.settings

import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
@InjectViewState
class SettingsPresenter @Inject constructor(
        @GlobalNavigationQualifier private val router: Router
) : BasePresenter<SettingsView>() {

    fun pressBack() {
        router.exit()
    }
}