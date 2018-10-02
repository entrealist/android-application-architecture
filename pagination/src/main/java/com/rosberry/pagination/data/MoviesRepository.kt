package com.rosberry.pagination.data

import com.rosberry.pagination.system.Movies
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Alexei Korshun on 28/09/2018.
 */
class MoviesRepository constructor(
        private val moviesApi: MoviesApi
) {

    fun movies(page: Int): Single<Movies> {
        return moviesApi.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}