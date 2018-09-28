package com.rosberry.android.sample.data.persistence.network

import com.rosberry.android.sample.data.persistence.network.dto.request.PostRequest
import com.rosberry.android.sample.data.persistence.network.dto.response.*
import io.reactivex.Single
import retrofit2.http.*


/**
 * @author neestell on 01/08/2018.
 */
interface PostApi {

    @GET("posts")
    fun getPosts(): Single<PostsResponse>

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Single<PostResponse>

    @GET("photos")
    fun getPhotos(): Single<PhotosResponse>

    @POST("posts")
    fun addPost(@Body postRequest: PostRequest): Single<PostResponse>
}