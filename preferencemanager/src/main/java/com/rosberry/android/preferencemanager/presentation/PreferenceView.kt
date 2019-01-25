/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.android.preferencemanager.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Alexei Korshun on 21/01/2019.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface PreferenceView : MvpView {

    fun name(name: String)
    fun surname(surname: String)
}