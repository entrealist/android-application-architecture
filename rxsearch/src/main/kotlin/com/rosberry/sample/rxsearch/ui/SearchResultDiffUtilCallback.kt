package com.rosberry.sample.rxsearch.ui

import android.support.v7.util.DiffUtil
import com.rosberry.abstractrecycler.AbstractItem
import com.rosberry.sample.rxsearch.presentation.model.SearchResultItem

/**
 * @author mmikhailov on 24.01.2019.
 */
class SearchResultDiffUtilCallback(
        private val oldItems: List<AbstractItem>,
        private val newItems: List<AbstractItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return if (oldItem.type == newItem.type
                && oldItem is SearchResultItem
                && newItem is SearchResultItem) {
            oldItem.id == newItem.id
        } else {
            false
        }
    }

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return if (oldItem is SearchResultItem && newItem is SearchResultItem) {
            oldItem.title == newItem.title
        } else {
            false
        }
    }
}