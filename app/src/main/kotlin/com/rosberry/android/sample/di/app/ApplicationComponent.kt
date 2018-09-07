package com.rosberry.android.sample.di.app

import android.content.Context
import com.rosberry.android.sample.di.data.DataComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author neestell on 7/09/2018.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun plusDataComponent(): DataComponent.Builder

}