package com.rosberry.notificationservice

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

/**
 * @author mmikhailov on 2019-09-10.
 */
class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}