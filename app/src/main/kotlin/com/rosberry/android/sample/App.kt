package com.rosberry.android.sample

import android.support.multidex.MultiDexApplication
import com.rosberry.android.sample.di.AndroidInjector
import com.rosberry.android.sample.di.AndroidInjector.openDataScope

class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidInjector.openAppScope(this).also { openDataScope() }

    }
}