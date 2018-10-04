package com.rosberry.navigation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int

    abstract fun onBackPressed() : Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)
}