/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import com.rosberry.sample.errorhandler.data.system.ResourceManager
import com.rosberry.sample.errorhandler.domain.SampleInteractor

/**
 * @author Alexei Korshun on 21/11/2018.
 */
class SampleInteractorFactory(
        private val resourceManager: ResourceManager
) {

    fun get() = SampleInteractor(resourceManager)

}