package com.rosberry.android.sample.data.transformer

import com.rosberry.android.sample.data.persistence.network.dto.response.*
import com.rosberry.android.sample.entity.Photo
import com.rosberry.android.sample.entity.Post
import javax.inject.Inject

class PostTransformer @Inject constructor() {

    fun toPosts(response: PostsResponse): ArrayList<Post> {
        return response.map { it -> Post(it.id, it.userId, it.title, it.description) }
            .toCollection(ArrayList())
    }

    fun toPost(response: PostResponse) : Post{
        return Post(response.id, response.userId, response.title, response.description)
    }

    fun toPhotos(response: PhotosResponse): ArrayList<Photo>? {
        return response.map { it -> Photo(it.id, it.albumId, it.title, it.url, it.thumbnailUrl) }
            .toCollection(ArrayList())
    }
}