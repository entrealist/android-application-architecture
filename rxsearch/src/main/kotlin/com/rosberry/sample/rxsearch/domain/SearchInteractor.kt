package com.rosberry.sample.rxsearch.domain

import android.util.Log
import com.rosberry.sample.rxsearch.data.network.ServiceApi
import com.rosberry.sample.rxsearch.data.network.dto.SearchResult
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author mmikhailov on 17/01/2019.
 */
class SearchInteractor @Inject constructor(
        private val serviceApi: ServiceApi
) {
    private val tag = "SearchInteractor"

    fun search(query: String): Single<List<SearchResult>> {
        Log.d(tag, "searchProductByName::$query")
        return serviceApi.search(query)
            .map { it.results }
    }
}