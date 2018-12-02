package com.rosberry.pagination.ui

import android.view.View
import com.rosberry.abstractrecycler.AbstractViewHolder
import com.rosberry.pagination.presentation.MovieItem
import kotlinx.android.synthetic.main.i_movie.view.*

/**
 * @author Alexei Korshun on 02/10/2018.
 */
class MovieViewHolder(view: View) : AbstractViewHolder<MovieItem>(view) {

    override fun bind(item: MovieItem) {
        super.bind(item)
        itemView.titleMovieView.text = item.movie.title
        itemView.releaseTextView.text = item.movie.releaseDate
    }
}