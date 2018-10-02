package com.rosberry.android.sample.presentation.post.add

import com.evernote.android.state.State
import com.rosberry.android.sample.data.persistence.internal.ViewDataRepository
import com.rosberry.android.sample.entity.Post
import javax.inject.Inject

class AddPostViewData @Inject constructor(@State var post: Post) : ViewDataRepository() {

    @State var newPost: Post? = null
}