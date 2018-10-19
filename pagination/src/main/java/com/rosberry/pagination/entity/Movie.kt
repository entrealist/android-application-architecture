package com.rosberry.pagination.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Alexei Korshun on 28/09/2018.
 */
data class Movie(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val title: String,
        @SerializedName("release_date") val releaseDate: String
)