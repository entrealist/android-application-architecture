/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.android.preferencemanager.data

import android.content.Context
import com.rosberry.android.preferencemanager.common.PreferenceManager

/**
 * @author Alexei Korshun on 21/01/2019.
 */
class SettingsManager constructor(context: Context) : PreferenceManager(context) {

    override val prefName: String = "SETTINGS_PREFERENCE"

    private val isLoadOnStartKey: String = "IS_LOAD_ON_START_KEY_PREF"

    var isLoadOnStart: Boolean
        get() = sharedPrefs().getBoolean(isLoadOnStartKey, false)
        set(isLoadOnStart) = sharedPrefs().edit().putBoolean(isLoadOnStartKey, isLoadOnStart).apply()
}