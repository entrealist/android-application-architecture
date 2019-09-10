package com.rosberry.sample.surfaceviewrxed.data

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
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