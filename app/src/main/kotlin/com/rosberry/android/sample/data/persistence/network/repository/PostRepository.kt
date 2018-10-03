package com.rosberry.android.sample.data.persistence.network.repository

import com.rosberry.android.sample.data.persistence.network.PostApi
import com.rosberry.android.sample.data.persistence.network.dto.request.PostRequest
import com.rosberry.android.sample.data.transformer.PostTransformer
import com.rosberry.android.sample.di.data.DataScope
import com.rosberry.android.sample.entity.Photo
import com.rosberry.android.sample.entity.Post
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author neestell on 7/09/2018.
 */
@DataScope
class PostRepository @Inject constructor(
        private val postApi: PostApi,
        private val postTransformer: PostTransformer
) {

    fun getPosts(): Single<ArrayList<Post>> {
        return postApi.getPosts()
            .map {postTransformer.toPosts(it) }
    }

    fun getPost(id: Int): Single<Post> {
        return postApi.getPost(id)
            .map {postTransformer.toPost(it) }
    }

    fun addPost(post: Post): Single<Post>{
        return postApi.addPost(PostRequest(post.userId, post.title, post.description))
            .map { postTransformer.toPost(it) }
    }

    fun getPhotos(): Single<ArrayList<Photo>>{
        return postApi.getPhotos().map { postTransformer.toPhotos(it) }
    }
}
