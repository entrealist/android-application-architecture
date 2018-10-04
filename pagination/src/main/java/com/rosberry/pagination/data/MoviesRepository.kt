package com.rosberry.pagination.data

import com.rosberry.pagination.system.Movies
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Alexei Korshun on 28/09/2018.
 */
class MoviesRepository constructor(private val moviesApi: MoviesApi) {

    fun movies(offset: Int): Single<Movies> {
        return moviesApi.getMovies(offset = offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}