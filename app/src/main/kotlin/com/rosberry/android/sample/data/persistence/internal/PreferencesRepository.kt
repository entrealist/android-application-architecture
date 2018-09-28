package com.rosberry.android.sample.data.persistence.internal

import android.content.SharedPreferences
import android.text.TextUtils
import com.rosberry.android.sample.BuildConfig
import com.rosberry.android.sample.system.TextEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(val prefs: SharedPreferences) {

    private val accessTokenKey = "access_token"

    private fun accessToken() = decodeString(accessTokenKey, "")

    fun putAccessToken(accessToken: String) = putEncodedString(accessTokenKey, accessToken)

    fun hasToken() = !TextUtils.isEmpty(accessToken())

    fun getBool(key: String, defValue: Boolean) = prefs.getBoolean(key, defValue)

    fun putBool(key: String, value: Boolean) = prefs.edit().putBoolean(key, value).apply()

    fun getString(key: String, defValue: String) = prefs.getString(key, defValue)

    fun putString(key: String, value: String) = prefs.edit().putString(key, value).apply()

    fun getInt(key: String, defValue: Int) = prefs.getInt(key, defValue)

    fun putInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()

    fun getDouble(key: String, defValue: Double) =
            prefs.getFloat(key, defValue.toFloat()).toDouble()

    fun putDouble(key: String, value: Double) = prefs.edit().putFloat(key, value.toFloat()).apply()

    fun getSet(key: String, defValue: Set<String>) = prefs.getStringSet(key, defValue)

    fun putSet(key: String, value: Set<String>) = prefs.edit().putStringSet(key, value).apply()

    fun getLong(key: String, defValue: Long) = prefs.getLong(key, defValue)

    fun putLong(key: String, value: Long) = prefs.edit().putLong(key, value).apply()

    fun putEncodedString(key: String, value: String) = prefs.edit()
        .putString(key, TextEncoder.encrypt(value, BuildConfig.RANDOM)).apply()


    fun decodeString(key: String, defValue: String): String {
        val result = prefs.getString(key, defValue)
        if (result === defValue || result == null)
            return ""
        return TextEncoder.decrypt(result, BuildConfig.RANDOM)
    }

    fun remove(key: String) = prefs.edit().remove(key).apply()

    fun reset() {
        prefs.edit()
            .clear()
            .apply()
    }

}