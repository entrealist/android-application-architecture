package com.rosberry.android.sample.data.transformer

import com.rosberry.android.sample.data.persistence.network.dto.response.*
import com.rosberry.android.sample.entity.Photo
import com.rosberry.android.sample.entity.Post
import javax.inject.Inject

class PostTransformer @Inject constructor() {

    fun toPosts(response: PostsResponse): ArrayList<Post> {
        return response.map { it -> Post(it.id?: 0, it.userId?:0, it.title?: "Empty", it.description?: "Empty") }
            .toCollection(ArrayList())
    }

    fun toPost(response: PostResponse) : Post{
        return Post(response.id?: 0, response.userId?:0, response.title?: "Empty", response.description?: "Empty")
    }

    fun toPhotos(response: PhotosResponse): ArrayList<Photo>? {
        return response.map { it -> Photo(it.id, it.albumId, it.title, it.url, it.thumbnailUrl) }
            .toCollection(ArrayList())
    }
}