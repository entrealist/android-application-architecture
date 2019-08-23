package com.rosberry.sample.workmanager

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.rosberry.sample.workmanager.di.Injector

/**
 * @author mmikhailov on 16/04/2019.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.openAppScope(applicationContext)
        AndroidThreeTen.init(this)
    }
}