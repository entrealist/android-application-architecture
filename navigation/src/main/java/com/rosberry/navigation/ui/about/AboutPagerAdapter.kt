package com.rosberry.navigation.ui.about

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rosberry.navigation.R

/**
 * @author mmikhailov on 02.10.2018.
 */
class AboutPagerAdapter(
        private val context: Context,
        private var items: MutableList<AboutItem> = ArrayList()
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.i_about_card, container, false)
        val item = items[position]

        view.findViewById<TextView>(R.id.label)
            .text = item.label

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount() = items.size

    fun setItems(items: List<AboutItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}