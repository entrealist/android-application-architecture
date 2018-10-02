package com.rosberry.android.sample.di

import android.content.Context
import com.rosberry.android.sample.BuildConfig
import com.rosberry.android.sample.di.app.ApplicationComponent
import com.rosberry.android.sample.di.app.DaggerApplicationComponent
import com.rosberry.android.sample.di.data.DataComponent
import com.rosberry.android.sample.di.post.PostComponent
import com.rosberry.android.sample.entity.Post

object AndroidInjector {

    private lateinit var appComponent: ApplicationComponent
    private var dataComponent: DataComponent? = null
    private var postComponent: PostComponent? = null

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

    fun openPostDetailsScope(post: Post) = openPostScope(post)
        .plusPostDetailsComponent()


    fun openAddPostScope(post: Post) = openPostScope(post)
        .plusAddPostComponent()

    fun openEditPostScope() = openPostScope(null).plusEditPostComponent()

    fun openPostScope(post: Post?): PostComponent {
        if (postComponent == null) {
            val postComponentBuilder = openDataScope().plusPostComponent()
            if (post != null)
                postComponent = postComponentBuilder.post(post)
                    .build()
            else postComponent = postComponentBuilder.build()
        }

        return postComponent!!
    }

    fun closeDataScope() {
        dataComponent = null
    }


}