package com.rosberry.sample.rxsearch.data.network.dto

/**
 * @author mmikhailov on 28/01/2019.
 */
data class SearchResponse(
        val resultCount: Int,
        val results: List<SearchResult>
)

data class SearchResult(
        val amgArtistId: Long,
        val artistId: String,
        val artistLinkUrl: String,
        val artistName: String,
        val artistType: String,
        val primaryGenreId: Long,
        val primaryGenreName: String,
        val wrapperType: String
)