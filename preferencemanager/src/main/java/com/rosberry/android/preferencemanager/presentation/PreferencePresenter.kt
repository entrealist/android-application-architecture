/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.android.preferencemanager.presentation

import com.rosberry.android.preferencemanager.data.SettingsManager
import com.rosberry.android.preferencemanager.data.UserManager
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * @author Alexei Korshun on 21/01/2019.
 */
@InjectViewState
class PreferencePresenter constructor(
        private val settingsManager: SettingsManager,
        private val userManager: UserManager
) : MvpPresenter<PreferenceView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (settingsManager.isLoadOnStart) {
            viewState.name(userManager.name)
            viewState.surname(userManager.surname)
        }
    }

    fun clickSaveButton(name: String, surname: String, isLoadOnStart: Boolean) {
        userManager.name = name
        userManager.surname = surname
        settingsManager.isLoadOnStart = isLoadOnStart
    }
}