package com.rosberry.android.sample.domain.main

import com.rosberry.android.sample.data.persistence.internal.ResourcesRepository
import com.rosberry.android.sample.data.persistence.network.repository.PostRepository
import com.rosberry.android.sample.entity.Post
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractor @Inject constructor(private val postRepository: PostRepository) {

    fun getPosts(): Single<ArrayList<Post>>{
        return postRepository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

}