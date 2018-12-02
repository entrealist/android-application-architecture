/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.presentation

import android.util.Log
import com.rosberry.sample.errorhandler.data.system.StringProvider

/**
 * @author Alexei Korshun on 29/10/2018.
 */
open class ErrorHandler (
        private val errorTitleProvider: StringProvider,
        private val errorDescriptionProvider: StringProvider
) {

    companion object {
        private const val TAG = "ErrorHandler"
    }

    fun proceed(error: Throwable, messageListener: (String, String) -> Unit = { _, _ -> }) {
        Log.e(TAG, error.message, error)
        val title: String = errorTitleProvider.get(error)
        val description: String = errorDescriptionProvider.get(error)

        messageListener.invoke(title, description)
    }
}