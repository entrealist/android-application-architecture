package com.rosberry.android.sample.di.post

import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.PostDetailsPresenter
import dagger.BindsInstance
import dagger.Subcomponent


/**
 * Created by neestell on 01.10.2018.
 * Rosberry Ltd
 */
@Subcomponent
interface PostComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun post(post: Post): Builder

        fun build(): PostComponent
    }

    fun getPostPresenter(): PostDetailsPresenter
}