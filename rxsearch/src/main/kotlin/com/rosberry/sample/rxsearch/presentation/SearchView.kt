package com.rosberry.sample.rxsearch.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rosberry.sample.rxsearch.presentation.model.SearchResultItem

/**
 * @author mmikhailov on 15/01/2019.
 */
interface SearchView : MvpView {

    companion object {
        private const val TAG_DISPLAY_INTERACTIVE_PLACEHOLDER = "TAG_DISPLAY_INTERACTIVE_PLACEHOLDER"
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateResultItems(items: List<SearchResultItem>)

    @StateStrategyType(SkipStrategy::class)
    fun showProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class, tag = TAG_DISPLAY_INTERACTIVE_PLACEHOLDER)
    fun showInteractivePlaceholder(title: String, description: String, actionText: String)

    @StateStrategyType(AddToEndSingleStrategy::class, tag = TAG_DISPLAY_INTERACTIVE_PLACEHOLDER)
    fun hideInteractivePlaceholder()

    @StateStrategyType(SkipStrategy::class)
    fun showToast(text: String)
}