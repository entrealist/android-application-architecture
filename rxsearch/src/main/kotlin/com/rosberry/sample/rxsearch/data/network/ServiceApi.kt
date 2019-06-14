package com.rosberry.sample.rxsearch.data.network

import com.rosberry.sample.rxsearch.data.network.dto.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author mmikhailov on 14.01.2019
 */
interface ServiceApi {

    @GET("search?country=RU&entity=allArtist&attribute=allArtistTerm")
    fun search(@Query("term") query: String): Single<SearchResponse>
}