/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import com.rosberry.sample.errorhandler.data.system.ErrorDescriptionProvider
import com.rosberry.sample.errorhandler.data.system.ResourceManager

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class ErrorDescriptionProviderFactory(
        private val resourceManager: ResourceManager
) {

    fun get() = ErrorDescriptionProvider(resourceManager)
}