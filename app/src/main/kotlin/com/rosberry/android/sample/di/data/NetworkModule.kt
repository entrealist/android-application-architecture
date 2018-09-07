package com.rosberry.android.sample.di.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rosberry.android.sample.BuildConfig
import com.rosberry.android.sample.data.persistence.network.PostApi
import com.rosberry.android.sample.data.persistence.network.UserApi
import com.rosberry.android.sample.data.persistence.network.interceptor.CurlLoggingInterceptor
import com.rosberry.android.sample.data.persistence.network.interceptor.ErrorInterceptor
import com.rosberry.android.sample.di.data.qualifier.AuthClient
import com.rosberry.android.sample.di.data.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author neestell on 7/09/2018.
 */
@Module
class NetworkModule {

    private val timeoutTime = 60L

    @DataScope
    @Provides
    fun providesGson() = GsonBuilder().create()

    @DataScope
    @Provides
    @AuthClient
    fun provideNonAuthOkHttpClient(errorInterceptor: ErrorInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(timeoutTime, TimeUnit.SECONDS)
                readTimeout(timeoutTime, TimeUnit.SECONDS)
                addInterceptor(errorInterceptor)
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    addNetworkInterceptor(CurlLoggingInterceptor())
                }
            }
            .build()
    }

    @DataScope
    @Provides
    fun providePostApi(gson: Gson,
                          @AuthClient okHttpClient: OkHttpClient,
                          @BaseUrl url: String): PostApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(url)
            .build()
            .create(PostApi::class.java)
    }

    @DataScope
    @Provides
    fun provideUserApi(gson: Gson,
                       @AuthClient okHttpClient: OkHttpClient,
                       @BaseUrl url: String): UserApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(url)
            .build()
            .create(UserApi::class.java)
    }

}