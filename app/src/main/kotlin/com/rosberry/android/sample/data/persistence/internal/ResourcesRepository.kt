package com.rosberry.android.sample.data.persistence.internal

import android.content.Context
import android.support.annotation.*
import android.support.v4.content.ContextCompat

class ResourcesRepository(val context: Context) {

    fun getString(stringRes: Int): String = context.getString(stringRes)

    fun getString(@StringRes str: Int, vararg args: Any): String = context.getString(str, *args)

    fun getArray(@ArrayRes array: Int): Array<String> = context.resources.getStringArray(array)

    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(context, color)

    fun getRaw(@RawRes raw: Int) = context.resources.openRawResource(raw)

    fun getAsset(value: String) = context.assets.open(value)
}