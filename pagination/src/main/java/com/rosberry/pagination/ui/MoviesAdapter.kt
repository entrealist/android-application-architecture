package com.rosberry.pagination.ui

import android.support.v7.util.DiffUtil
import android.view.View
import com.rosberry.pagination.R
import com.rosberry.pagination.presentation.ProgressItem

/**
 * @author Alexei Korshun on 02/10/2018.
 */
class MoviesAdapter(
        movies: List<AbstractItem>
) : AbstractRecyclerAdapter(movies) {

    override fun createViewHolder(view: View, viewType: Int): AbstractViewHolder<*> {
        return when (viewType) {
            R.layout.i_movie -> MovieViewHolder(view)
            else -> ProgressViewHolder(view)
        }
    }

    fun showData(newData: List<AbstractItem>) {
        val oldItems = items.toList()
        val result = DiffUtil.calculateDiff(MovieDiffUtil(oldItems, newData))

        items = newData
        result.dispatchUpdatesTo(this)
    }

    fun showProgress(show: Boolean) {
        if (show.and(!isShowProgress())) {
            val newItems = items.toMutableList()
            newItems.add(ProgressItem())
            showData(newItems)
        } else if (!show.and(isShowProgress())) {
            val newItems = items.toMutableList()
            newItems.removeAll { it.type == R.layout.i_progress }
            showData(newItems)
        }
    }

    private fun isShowProgress() = items.last().type == R.layout.i_progress
}