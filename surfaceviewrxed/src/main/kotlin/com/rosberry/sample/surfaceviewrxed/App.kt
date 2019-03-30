package com.rosberry.sample.surfaceviewrxed

import android.app.Application
import com.rosberry.sample.surfaceviewrxed.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.openAppScope(this)
    }
}