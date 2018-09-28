package com.rosberry.android.sample.data.persistence.network

import android.service.autofill.UserData
import io.reactivex.Single
import retrofit2.http.GET


/**
 * @author neestell on 01/08/2018.
 */
interface UserApi {

    @GET("users")
    fun getUsers(): Single<List<UserData>>

}