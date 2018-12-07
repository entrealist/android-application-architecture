/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import com.rosberry.sample.errorhandler.data.system.ErrorTitleProvider
import com.rosberry.sample.errorhandler.data.system.ResourceManager

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class ErrorTitleProviderFactory(
        private val resourceManager: ResourceManager
) {

    fun get() = ErrorTitleProvider(resourceManager)
}