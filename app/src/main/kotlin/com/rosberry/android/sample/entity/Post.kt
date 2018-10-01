package com.rosberry.android.sample.entity

import java.io.Serializable

class Post(
        val id: Int,
        val userId: Int,
        val title: String,
        val description: String
) : Serializable {
}