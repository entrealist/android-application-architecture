/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.data.system

import android.content.Context

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class ResourceManager(
        private val context: Context
) {

    fun getString(id: Int): String = context.getString(id)

    fun getString(id: Int, vararg params: Any): String = context.getString(id, *params)
}