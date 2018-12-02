/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import com.rosberry.sample.errorhandler.domain.SampleInteractor
import com.rosberry.sample.errorhandler.presentation.ErrorHandler
import com.rosberry.sample.errorhandler.presentation.ErrorPresenter

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class PresenterFactory(
        private val errorHandler: ErrorHandler,
        private val sampleInteractor: SampleInteractor
) {

    fun get() = ErrorPresenter(errorHandler, sampleInteractor)
}