package com.rosberry.sample.workmanager.di

import android.content.Context

/**
 * @author mmikhailov on 16/04/2019.
 */
object Injector {

    lateinit var appComponent: ApplicationComponent

    fun openAppScope(context: Context): ApplicationComponent {
        if (!Injector::appComponent.isInitialized) {
            appComponent = DaggerApplicationComponent.factory()
                .create(context)
        }

        return appComponent
    }
}