package com.rosberry.viewpager.fragmentpager.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rosberry.viewpager.R

/**
 * @author mmikhailov on 12.10.2018.
 */
class Tab1Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_tab_1, container, false)
    }

    fun doSmth() {

    }
}