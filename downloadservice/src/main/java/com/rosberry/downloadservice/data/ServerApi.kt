/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.downloadservice.data

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * @author neestell on 2019-10-10.
 */
interface ServerApi {

    @Multipart
    @POST("post")
    fun uploadFile(
            @Part file: MultipartBody.Part,
            @Part("name") requestBody: RequestBody
    ): Completable

    @GET("bytes/{size}")
    fun downloadFile(
            @Path("size") size: Long = TimeUnit.SECONDS.toMillis(10)
    ): Single<ResponseBody>
}
