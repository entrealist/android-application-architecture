package com.rosberry.android.sample.di.app

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author neestell on 7/09/2018.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("common_prefs", MODE_PRIVATE)
    }
}