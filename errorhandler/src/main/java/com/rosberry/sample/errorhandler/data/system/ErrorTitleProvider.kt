/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.data.system

import com.rosberry.sample.errorhandler.R
import java.io.IOException
import java.net.UnknownHostException

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class ErrorTitleProvider(
        private val resourceManager: ResourceManager
) : StringProvider {

    override fun get(error: Throwable): String {
        val resource: Int = when (error) {
            is IllegalStateException -> R.string.core_illegal_state_title
            is UnknownHostException -> R.string.core_unknown_host_title
            is IOException -> R.string.core_network_error_title
            else -> R.string.core_unknown_error_title
        }

        return resourceManager.getString(resource)
    }
}