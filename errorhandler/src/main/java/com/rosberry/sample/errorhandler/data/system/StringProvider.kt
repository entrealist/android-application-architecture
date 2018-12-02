/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.data.system

/**
 * @author Alexei Korshun on 29/10/2018.
 */
interface StringProvider {

    fun get(error: Throwable): String
}