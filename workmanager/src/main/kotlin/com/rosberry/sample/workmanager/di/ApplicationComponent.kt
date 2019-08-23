package com.rosberry.sample.workmanager.di

import android.content.Context
import com.rosberry.sample.workmanager.system.TimeChangedBroadcastReceiver
import com.rosberry.sample.workmanager.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author mmikhailov on 16/04/2019.
 */
@Singleton
@Component(modules = [DatabaseModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(target: MainActivity)
    fun inject(target: TimeChangedBroadcastReceiver)
}