package com.rosberry.sample.rxsearch.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rosberry.sample.rxsearch.data.network.ServiceApi
import com.rosberry.sample.rxsearch.data.network.interceptor.CheckConnectionInterceptor
import com.rosberry.sample.rxsearch.data.network.interceptor.ResponseInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author mmikhailov on 14.01.2019
 */
@Module
class NetworkModule {

    private val cacheSizeInMb = 20L
    private val timeoutTime = 60L

    @Singleton
    @Provides
    fun provideAuthOkHttpClient(
            context: Context,
            responseInterceptor: ResponseInterceptor,
            checkConnectionInterceptor: CheckConnectionInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .apply {
                cache(Cache(context.cacheDir, cacheSizeInMb * 1024))
                connectTimeout(timeoutTime, TimeUnit.SECONDS)
                readTimeout(timeoutTime, TimeUnit.SECONDS)
                addInterceptor(responseInterceptor)
                addInterceptor(checkConnectionInterceptor)
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            .build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
    }

    @Singleton
    @Provides
    fun provideServiceApi(
            gson: Gson,
            okHttpClient: OkHttpClient,
            @BaseUrl path: String
    ): ServiceApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .baseUrl(path)
            .build()
            .create(ServiceApi::class.java)
    }
}