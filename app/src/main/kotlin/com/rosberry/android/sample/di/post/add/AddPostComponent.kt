package com.rosberry.android.sample.di.post.add

import com.rosberry.android.sample.di.post.PostScope
import com.rosberry.android.sample.entity.Post
import com.rosberry.android.sample.presentation.post.add.AddPostPresenter
import com.rosberry.android.sample.ui.post.add.AddPostActivity
import dagger.BindsInstance
import dagger.Subcomponent


/**
 * Created by neestell on 01.10.2018.
 * Rosberry Ltd
 */
@Subcomponent
interface AddPostComponent {

    fun provideAddPostPresenter(): AddPostPresenter
}