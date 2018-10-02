package com.rosberry.android.sample.di.post.edit

import com.rosberry.android.sample.presentation.post.edit.EditPostPresenter
import com.rosberry.android.sample.ui.post.add.AddPostActivity
import dagger.Subcomponent


/**
 * Created by neestell on 02.10.2018.
 * Rosberry Ltd
 */
@Subcomponent
interface EditPostComponent {

    fun provideEditPostPresenter(): EditPostPresenter
}