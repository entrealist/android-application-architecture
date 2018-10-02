package com.rosberry.android.sample.di

import android.content.Context
import com.rosberry.android.sample.BuildConfig
import com.rosberry.android.sample.di.app.ApplicationComponent
import com.rosberry.android.sample.di.app.DaggerApplicationComponent
import com.rosberry.android.sample.di.data.DataComponent
import com.rosberry.android.sample.entity.Post

object AndroidInjector {

    private lateinit var appComponent: ApplicationComponent
    private var dataComponent: DataComponent? = null

    fun openAppScope(context: Context): ApplicationComponent {
        if (!::appComponent.isInitialized) {
            appComponent = DaggerApplicationComponent.builder()
                .application(context)
                .build()
        }
        return appComponent
    }

    fun openDataScope(): DataComponent {
        if (dataComponent == null) {
            dataComponent = appComponent.plusDataComponent()
                .setBaseUrl(BuildConfig.BASE_URL)
                .build()
        }

        return dataComponent!!
    }

    fun openMainScope() = openDataScope().plusMainComponent()

    fun openPostScope(userPost: Post) = openDataScope().plusPostComponent().post(userPost).build()

    fun openAddPostScope(userPost: Post) = openDataScope().plusAddPostComponent().post(userPost).build()

    fun closeDataScope() {
        dataComponent = null
    }

    fun openEditPostScope() = openDataScope().plusEditPostComponent()

}