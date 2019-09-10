package com.rosberry.notificationservice.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rosberry.notificationservice.R
import com.rosberry.notificationservice.extension.smoothScrollBottom
import com.rosberry.notificationservice.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.a_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)

        timeRoll.setListeners(
                changeTimeListener = { tag, h, m -> viewModel.changeItemTime(tag, h, m) },
                buttonClickListener = { viewModel.clickItemButton(it) }
        )
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        viewModel.timeRollItems.observe(this, Observer {
            timeRoll.updateItems(it) { nestedScrollView.smoothScrollBottom(250) }
        })
    }
}
