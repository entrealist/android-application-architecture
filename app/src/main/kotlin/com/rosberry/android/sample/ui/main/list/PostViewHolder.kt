package com.rosberry.android.sample.ui.main.list

import android.view.*
import com.rosberry.android.sample.R
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.i_post.view.*

class PostViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.i_post) {

    fun bind(item: PostItem) {
        itemView.textTitle.text = item.title
        itemView.textDescription.text = item.description
    }

}