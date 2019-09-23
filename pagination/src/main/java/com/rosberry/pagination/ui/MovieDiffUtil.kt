package com.rosberry.pagination.ui

import androidx.recyclerview.widget.DiffUtil
import com.rosberry.abstractrecycler.AbstractItem

/**
 * @author Alexei Korshun on 02/10/2018.
 */
class MovieDiffUtil(
        private val oldItems: List<AbstractItem>,
        private val newItems: List<AbstractItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem = oldItems[oldPosition]
        val newItem = newItems[newPosition]

        return oldItem == newItem
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem = oldItems[oldPosition]
        val newItem = newItems[newPosition]

        return oldItem == newItem
    }

}