package com.rosberry.android.sample.ui.base

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.*

abstract class BaseViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
        RecyclerView.ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false))