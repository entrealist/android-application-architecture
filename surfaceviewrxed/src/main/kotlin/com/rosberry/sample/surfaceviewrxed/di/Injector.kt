package com.rosberry.sample.surfaceviewrxed.di

import android.content.Context
import com.rosberry.sample.surfaceviewrxed.di.app.ApplicationComponent
import com.rosberry.sample.surfaceviewrxed.di.app.DaggerApplicationComponent
import com.rosberry.sample.surfaceviewrxed.di.main.MainComponent

/**
 * @author mmikhailov on 28/03/2019.
 */
object Injector {

    lateinit var appComponent: ApplicationComponent
    var mainComponent: MainComponent? = null

    fun openAppScope(context: Context): ApplicationComponent {
        if (Injector::appComponent.isInitialized.not()) {
            appComponent = DaggerApplicationComponent.builder()
                .application(context)
                .build()
        }

        return appComponent
    }

    fun openMainScope(): MainComponent {
        return mainComponent
            ?: appComponent.plusMainComponent()
                .also { mainComponent = it }
    }

    fun closeMainScope() {
        mainComponent = null
    }
}