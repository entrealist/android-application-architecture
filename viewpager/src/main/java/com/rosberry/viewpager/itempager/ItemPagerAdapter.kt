package com.rosberry.viewpager.itempager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rosberry.viewpager.R
import kotlinx.android.synthetic.main.i_card.view.*

/**
 * @author mmikhailov on 12.10.2018.
 */
class ItemPagerAdapter(
        private val context: Context,
        private var items: MutableList<Item> = ArrayList()
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.i_card, container, false)
        val item = items[position]

        view.label.text = item.label
        view.subLabel.text = item.subLabel

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount() = items.size

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}