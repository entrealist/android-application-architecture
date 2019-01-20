package com.rosberry.navigation.di.data

import android.content.Context
import android.os.Build
import com.rosberry.navigation.BuildConfig
import com.rosberry.navigation.di.data.qualifier.AppVersion
import com.rosberry.navigation.di.data.qualifier.Dpi
import com.rosberry.navigation.di.data.qualifier.OsVersion
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