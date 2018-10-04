package com.rosberry.android.sample.ui.base

import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

/**
 * Created by Evgeniy Nagibin on 08/08/2017.
 */
abstract class BaseAdapter<V, M, L> : RecyclerView.Adapter<V> where V : BaseViewHolder {
    val handler = Handler()

    interface OnItemClickListener<in M> {
        fun onItemClick(model: M)
    }

    var items: ArrayList<M> = ArrayList()
    var itemClickListener: L? = null

    constructor(listener: L) : super() {
        this.itemClickListener = listener
    }

    constructor(items: List<M>) : super() {
        this.items.clear()
        this.items.addAll(items)
    }

    constructor() : super()

    constructor(listener: L, items: List<M>) {
        this.items.clear()
        this.items.addAll(items)
        this.itemClickListener = listener
    }

    open fun submitList(newItems: List<M>, animate: Boolean = true) {
        if (animate){
            val diffUtilCallback = provideDiffUtilCallback(this.items, newItems)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            Handler().post {
                this.items.clear()
                this.items.addAll(newItems)
                diffResult.dispatchUpdatesTo(this)
            }
        }else{
            this.items.clear()
            this.items.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): M = items[position]

    fun notifyItemChanged(item: M) {
        for (i in 0..items.size) {
            if (items[i] != null && items[i] == item) {
                notifyItemChanged(i)
                break
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(listener: L) {
        this.itemClickListener = listener
    }

    open fun provideDiffUtilCallback(oldList: List<M>, newList: List<M>): BaseDiffCallback<M> {
        return BaseDiffCallback(oldList, newList)
    }

    open class BaseDiffCallback<in M>(private val oldList: List<M>, private val newList: List<M>)
        : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]?.equals(newList[newItemPosition])?: false
        }

    }
}