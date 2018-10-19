package com.rosberry.pagination.data

import com.rosberry.pagination.system.Movies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Alexei Korshun on 28/09/2018.
 */
interface MoviesApi {

    @GET("/api/movie/")
    fun getMovies(
            @Query("offset") offset: Int = 0,
            @Query("limit") limit: Int = 10
    ): Single<Movies>
}