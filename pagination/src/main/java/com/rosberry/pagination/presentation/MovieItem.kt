package com.rosberry.pagination.presentation

import com.rosberry.pagination.R
import com.rosberry.pagination.entity.Movie
import ru.rosberry.abstractrecycler.AbstractItem

/**
 * @author Alexei Korshun on 02/10/2018.
 */
data class MovieItem(val movie: Movie) : AbstractItem(R.layout.i_movie)