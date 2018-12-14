package com.rosberry.sample.roomdatabase

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * @author mmikhailov on 12.11.2018.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}