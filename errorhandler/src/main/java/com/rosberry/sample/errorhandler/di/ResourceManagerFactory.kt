/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import android.content.Context
import com.rosberry.sample.errorhandler.data.system.ResourceManager

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class ResourceManagerFactory(
        private val context: Context
) {

    private val resourceManager by lazy { ResourceManager(context) }

    fun get() = resourceManager
}