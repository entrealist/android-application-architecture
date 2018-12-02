/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import com.rosberry.sample.errorhandler.data.system.StringProvider
import com.rosberry.sample.errorhandler.presentation.ErrorHandler

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class ErrorHandlerFactory(
        private val errorTitleProvider: StringProvider,
        private val errorDescriptionProvider: StringProvider
) {

    fun get() = ErrorHandler(errorTitleProvider, errorDescriptionProvider)
}