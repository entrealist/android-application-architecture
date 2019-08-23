package com.rosberry.sample.surfaceviewrxed.data

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author mmikhailov on 09/04/2019.
 */
@Singleton
class ResourceManager @Inject constructor(
        private val context: Context
) {

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }
}