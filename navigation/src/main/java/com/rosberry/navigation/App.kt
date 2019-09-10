package com.rosberry.navigation

import androidx.multidex.MultiDexApplication
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.AndroidInjector.openDataScope

class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidInjector.openAppScope(this).also { openDataScope() }

    }
}