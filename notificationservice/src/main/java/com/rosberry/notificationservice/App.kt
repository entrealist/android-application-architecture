package com.rosberry.notificationservice

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * @author mmikhailov on 2019-09-10.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}