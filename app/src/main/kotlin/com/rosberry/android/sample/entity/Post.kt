package com.rosberry.android.sample.entity

import java.io.Serializable

class Post(
        val id: Int,
        val userId: Int,
        var title: String,
        var description: String
) : Serializable {
}