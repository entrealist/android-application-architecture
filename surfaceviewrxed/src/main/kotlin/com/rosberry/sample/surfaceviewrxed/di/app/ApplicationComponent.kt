package com.rosberry.sample.surfaceviewrxed.di.app

import android.content.Context
import com.rosberry.sample.surfaceviewrxed.di.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author mmikhailov on 28/03/2019.
 */
@Singleton
@Component
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun plusMainComponent(): MainComponent.Builder

}