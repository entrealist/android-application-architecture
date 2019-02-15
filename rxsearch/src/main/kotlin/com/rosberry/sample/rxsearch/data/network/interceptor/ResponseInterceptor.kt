package com.rosberry.sample.rxsearch.data.network.interceptor

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author mmikhailov on 14.01.2019
 */
@Singleton
class ResponseInterceptor @Inject constructor(
        private val gson: Gson
) : Interceptor {

    private val tag = "ResponseInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val code = response.code()
        Log.d(tag, "intercept::$code")
        when (code) {
            in 400..500 -> {
                throw HttpException(retrofit2.Response.error<Any>(response.body()!!, response))
            }
        }

        return response
    }

}