package com.rosberry.android.sample.presentation.main.list

data class PostItem(val id: Int,
               val title: String,
               val description: String
               ) {
    var isSelected = false
}