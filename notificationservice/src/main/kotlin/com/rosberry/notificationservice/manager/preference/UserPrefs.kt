package com.rosberry.notificationservice.manager.preference

import android.content.Context

/**
 * @author mmikhailov on 2019-09-11.
 */
class UserPrefs(context: Context) : PreferenceManager(context) {

    override val prefName: String = "SHARED_PREFERENCES_USER"

    private val timetableKey = "KEY_PREF_TIMETABLE"

    var timetable: Set<String>
        get() = getStringSet(timetableKey)
        set(value) = putStringSet(timetableKey, value)
}