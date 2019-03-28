package com.rosberry.sample.rxsearch.presentation.model

import com.rosberry.abstractrecycler.AbstractItem
import com.rosberry.sample.rxsearch.R

data class SearchResultItem(
        val id: String,
        val title: String
) : AbstractItem(R.layout.i_search_result)
