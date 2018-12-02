/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.di

import android.content.Context

/**
 * @author Alexei Korshun on 29/10/2018.
 */
class Component(context: Context) {

    private val resourceManagerFactory = ResourceManagerFactory(context)
    private val sampleInteractorFactory = SampleInteractorFactory(resourceManagerFactory.get())

    private val errorTitleProviderFactory = ErrorTitleProviderFactory(resourceManagerFactory.get())
    private val errorDescriptionProviderFactory = ErrorDescriptionProviderFactory(resourceManagerFactory.get())

    private val errorHandlerFactory = ErrorHandlerFactory(errorTitleProviderFactory.get(),
            errorDescriptionProviderFactory.get())

    private val presenterFactory = PresenterFactory(errorHandlerFactory.get(), sampleInteractorFactory.get())

    fun providePresenter() = presenterFactory.get()
}