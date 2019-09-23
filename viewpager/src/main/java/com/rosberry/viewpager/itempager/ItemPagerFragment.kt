package com.rosberry.viewpager.itempager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rosberry.viewpager.R
import kotlinx.android.synthetic.main.f_item_pager.*

/**
 * @author mmikhailov on 12.10.2018.
 */
class ItemPagerFragment : androidx.fragment.app.Fragment() {

    private val pagerAdapter: ItemPagerAdapter by lazy { ItemPagerAdapter(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_item_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        floodAdapter()
    }

    private fun setupViews() {
        viewPager.adapter = pagerAdapter
        viewPagerIndicator.attachToViewPager(viewPager)
    }

    private fun floodAdapter() {
        val items = mutableListOf<Item>()
        val alphabet = "abcdefghijk"
        for (i in 0..10) {
            items.add(Item(i.toString(), alphabet[i].toString()))
        }
        pagerAdapter.setItems(items)
    }
}