package com.rosberry.sample.rxsearch.data.network.interceptor

import com.rosberry.sample.rxsearch.data.manager.NetworkConnectionManager
import com.rosberry.sample.rxsearch.error.NetworkUnavailableException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author mmikhailov on 22.01.2019
 */
@Singleton
class CheckConnectionInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!NetworkConnectionManager.isOnline()) {
            throw NetworkUnavailableException()
        } else {
            chain.proceed(chain.request())
        }
    }
}