package com.rosberry.android.sample.di.post

import com.rosberry.android.sample.di.post.add.AddPostComponent
import com.rosberry.android.sample.di.post.detail.PostDetailsComponent
import com.rosberry.android.sample.di.post.edit.EditPostComponent
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.add.AddPostPresenter
import com.rosberry.android.sample.ui.post.add.AddPostActivity
import dagger.BindsInstance
import dagger.Subcomponent


/**
 * Created by neestell on 01.10.2018.
 * Rosberry Ltd
 */
@PostScope
@Subcomponent
interface PostComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun post(post: Post): Builder

        fun build(): PostComponent
    }

    fun plusEditPostComponent(): EditPostComponent

    fun plusPostDetailsComponent(): PostDetailsComponent

    fun plusAddPostComponent(): AddPostComponent

}