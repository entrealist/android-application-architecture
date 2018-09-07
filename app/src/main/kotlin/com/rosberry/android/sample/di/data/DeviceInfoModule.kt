package com.rosberry.android.sample.di.data

import android.content.Context
import android.os.Build
import com.rosberry.android.sample.BuildConfig
import com.rosberry.android.sample.di.data.qualifier.*
import dagger.Module
import dagger.Provides

/**
 * @author Alexei Korshun on 05/04/2018.
 */
@Module
class DeviceInfoModule {

    @Provides
    @OsVersion
    fun provideOsVersion(): String {
        return Build.VERSION.RELEASE
    }

    @Provides
    @AppVersion
    fun provideAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    @Provides
    @Dpi
    fun provideDpi(context: Context): String {
        return context.resources.displayMetrics.density.toString()
    }
}