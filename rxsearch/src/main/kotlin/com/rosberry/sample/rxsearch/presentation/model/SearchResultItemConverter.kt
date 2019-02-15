package com.rosberry.sample.rxsearch.presentation.model

import com.rosberry.sample.rxsearch.data.network.dto.SearchResult
import retrofit2.Converter
import javax.inject.Inject

/**
 * @author mmikhailov on 28.01.2019
 */
class SearchResultItemConverter @Inject constructor(

) : Converter<SearchResult, SearchResultItem> {

    override fun convert(item: SearchResult): SearchResultItem {
        return SearchResultItem(item.artistId, item.artistName)
    }

    fun convertList(items: List<SearchResult>): List<SearchResultItem> {
        return items.asSequence()
            .map { convert(it) }
            .toList()
    }
}