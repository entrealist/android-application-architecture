package com.rosberry.android.sample.ui.main.list

import android.view.ViewGroup
import com.rosberry.android.sample.presentation.main.list.PostItem
import com.rosberry.android.sample.ui.base.BaseAdapter

class PostsAdapter(itemClickListener: OnItemClickListener? = null)
    : BaseAdapter<PostViewHolder, PostItem, PostsAdapter.OnItemClickListener>() {

    init {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, pos: Int) {
        viewHolder.bind(getItem(pos))
        viewHolder.itemView.setOnClickListener {
            val item = items[viewHolder.adapterPosition];
            itemClickListener?.onPostClicked(item, pos)
        }
    }

    interface OnItemClickListener {
        fun onPostClicked(postItem: PostItem, pos: Int)
    }
}