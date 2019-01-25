/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.android.preferencemanager.common

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Alexei Korshun on 21/01/2019.
 */
abstract class PreferenceManager constructor(private val context: Context) {

    protected abstract val prefName: String

    fun sharedPrefs() : SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
}