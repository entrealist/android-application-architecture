package com.rosberry.sample.rxsearch

import android.app.Application
import com.rosberry.sample.rxsearch.di.AndroidInjector

/**
 * @author mmikhailov on 15/02/2019.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidInjector.openAppScope(this)
    }
}