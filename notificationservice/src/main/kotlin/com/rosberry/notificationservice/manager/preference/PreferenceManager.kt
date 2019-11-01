/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.notificationservice.manager.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Alexei Korshun on 21/01/2019.
 */
abstract class PreferenceManager constructor(private val context: Context) {

    protected abstract val prefName: String

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: Any) {
        when (value) {
            is String -> prefs.edit().putString(key, value).apply()
            is Int -> prefs.edit().putInt(key, value).apply()
            is Long -> prefs.edit().putLong(key, value).apply()
            is Boolean -> prefs.edit().putBoolean(key, value).apply()
        }
    }

    fun putStringSet(key: String, value: Set<String>) {
        prefs.edit()
            .putStringSet(key, value)
            .apply()
    }

    fun getStringSet(key: String, defValue: Set<String> = emptySet()): Set<String> =
            prefs.getStringSet(key, defValue) ?: emptySet()

    fun getString(key: String, defValue: String = ""): String =
            prefs.getString(key, defValue) ?: defValue

    fun getInt(key: String, defValue: Int = 0): Int = prefs.getInt(key, defValue)

    fun getLong(key: String, defValue: Long = 0L): Long = prefs.getLong(key, defValue)

    fun getBoolean(key: String, defValue: Boolean = false): Boolean =
            prefs.getBoolean(key, defValue)
}