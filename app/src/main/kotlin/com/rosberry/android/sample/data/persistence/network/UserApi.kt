package com.rosberry.android.sample.data.persistence.network

import com.rosberry.android.sample.data.persistence.network.dto.response.*
import io.reactivex.Single
import retrofit2.http.*


/**
 * @author neestell on 01/08/2018.
 */
interface UserApi {

    @GET("users")
    fun getUsers(): Single<UsersResponse>

}