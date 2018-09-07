package com.rosberry.android.sample.data.persistence.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author neestell on 7/09/2018.
 */
class ClientInterceptor
@Inject constructor(
        private val osVersion: String,
        private val appVersion: String,
        private val dpi: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        request = request.newBuilder()
                .addHeader("user-agent", "Android $osVersion version $appVersion")
                .addHeader("dpi", dpi)
                .build()

        return chain.proceed(request)

    }
}