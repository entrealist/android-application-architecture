package com.rosberry.sample.rxsearch.di

import android.content.Context
import com.rosberry.sample.rxsearch.presentation.SearchPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author mmikhailov on 09.12.2018.
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        @BindsInstance
        fun baseUrl(@BaseUrl url: String): Builder

        fun build(): ApplicationComponent
    }

    fun context(): Context

    fun provideSearchPresenter(): SearchPresenter
}