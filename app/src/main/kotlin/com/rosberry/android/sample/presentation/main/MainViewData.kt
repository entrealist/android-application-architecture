package com.rosberry.android.sample.presentation.main

import com.rosberry.android.sample.data.persistence.internal.ViewDataRepository
import com.rosberry.android.sample.entity.Post
import javax.inject.Inject

class MainViewData @Inject constructor() : ViewDataRepository() {
    var posts =  ArrayList<Post>()
}