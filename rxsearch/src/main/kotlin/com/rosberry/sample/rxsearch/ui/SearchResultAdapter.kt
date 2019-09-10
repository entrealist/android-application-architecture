package com.rosberry.sample.rxsearch.ui

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.rosberry.abstractrecycler.AbstractItem
import com.rosberry.abstractrecycler.AbstractRecyclerAdapter
import com.rosberry.abstractrecycler.AbstractViewHolder
import com.rosberry.sample.rxsearch.presentation.model.SearchResultItem
import kotlinx.android.synthetic.main.i_search_result.view.*

/**
 * @author mmikhailov on 24/01/2019.
 */
class SearchResultAdapter(
        private val itemClickListener: (String) -> Unit
) : AbstractRecyclerAdapter(emptyList()) {

    override fun createViewHolder(view: View, viewType: Int): AbstractViewHolder<*> {
        return SearchResultViewHolder(view, itemClickListener)
    }

    fun updateItems(newItems: List<AbstractItem>) {
        val diffResult = DiffUtil.calculateDiff(
                SearchResultDiffUtilCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }
}

class SearchResultViewHolder(
        view: View,
        private val itemClickListener: (String) -> Unit
) : AbstractViewHolder<SearchResultItem>(view) {

    override fun bind(item: SearchResultItem) {
        itemView.searchResultTitle.text = item.title
        itemView.setOnClickListener { itemClickListener.invoke(item.id) }
    }
}