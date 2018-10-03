package com.rosberry.android.sample.data.persistence.network.interceptor

import com.google.gson.Gson
import com.rosberry.android.sample.data.persistence.exception.NetworkException
import com.rosberry.android.sample.data.persistence.exception.ServerException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author neestell on 10/09/2018.
 */
class ErrorInterceptor @Inject constructor(
        private val gson: Gson
) : Interceptor {

    companion object {
        private const val PAYMENT_ERROR_CODE = 419
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val code = response.code()

        when (code) {
            in 200..226 -> {}
            in 400..600 -> {
                throw ServerException(retrofit2.Response.error<Any>(response.body()!!, response))
            }
            else -> throw NetworkException()

        }

        return response
    }

}