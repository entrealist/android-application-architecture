package com.rosberry.viewpager.fragmentpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rosberry.viewpager.R
import kotlinx.android.synthetic.main.f_fragment_pager.*

/**
 * @author mmikhailov on 12.10.2018.
 */
class FragmentPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        viewPager.adapter = FragmentPagerAdapter(childFragmentManager, requireContext())
        tabLayout.setupWithViewPager(viewPager)
    }
}