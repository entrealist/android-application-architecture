package com.rosberry.android.sample.di.post.add

import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.PostDetailsPresenter
import com.rosberry.android.sample.presentation.post.add.AddPostPresenter
import dagger.BindsInstance
import dagger.Subcomponent


/**
 * Created by neestell on 01.10.2018.
 * Rosberry Ltd
 */
@Subcomponent
interface AddPostComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun post(post: Post): Builder

        fun build(): AddPostComponent
    }

    fun getAddPostPresenter(): AddPostPresenter
}