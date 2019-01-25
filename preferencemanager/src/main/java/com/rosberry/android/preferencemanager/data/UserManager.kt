/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.android.preferencemanager.data

import android.content.Context
import com.rosberry.android.preferencemanager.common.PreferenceManager

/**
 * @author Alexei Korshun on 21/01/2019.
 */
class UserManager(context: Context) : PreferenceManager(context) {

    override val prefName: String = "USER_PREFERENCE"

    private val userNameKey = "USER_NAME_KEY_PREF"
    private val userSurnameKey = "USER_SURNAME_KEY_PREF"

    var name: String
        get() = sharedPrefs().getString(userNameKey, "") ?: ""
        set(name) = sharedPrefs().edit().putString(userNameKey, name).apply()

    var surname: String
        get() = sharedPrefs().getString(userSurnameKey, "") ?: ""
        set(surname) = sharedPrefs().edit().putString(userSurnameKey, surname).apply()
}