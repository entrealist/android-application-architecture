package com.rosberry.android.sample.di.post.detail

import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.detail.PostDetailsPresenter
import dagger.BindsInstance
import dagger.Subcomponent


/**
 * Created by neestell on 01.10.2018.
 * Rosberry Ltd
 */
@Subcomponent
interface PostDetailsComponent {

    fun providePostDetailsPresenter(): PostDetailsPresenter
}