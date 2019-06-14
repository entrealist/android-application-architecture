package com.rosberry.sample.rxsearch.di

import android.content.Context
import com.rosberry.sample.rxsearch.BuildConfig

object AndroidInjector {

    lateinit var appComponent: ApplicationComponent

    fun openAppScope(context: Context): ApplicationComponent {
        if (!AndroidInjector::appComponent.isInitialized) {
            appComponent = DaggerApplicationComponent.builder()
                .application(context)
                .baseUrl(BuildConfig.API_URL)
                .build()
        }

        return appComponent
    }
}