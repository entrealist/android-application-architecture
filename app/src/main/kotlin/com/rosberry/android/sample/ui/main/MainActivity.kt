package com.rosberry.android.sample.ui.main

import android.os.Bundle
import com.rosberry.android.sample.R
import com.rosberry.android.sample.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    init {
        model.backId = R.drawable.md_nav_back
        model.layoutId = R.layout.a_main
        model.menuId = R.menu.m_main
        model.title = R.string.app_name
        model.toolbarId = R.id.toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}
