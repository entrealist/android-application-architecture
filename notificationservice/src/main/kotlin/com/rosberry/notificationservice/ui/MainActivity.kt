package com.rosberry.notificationservice.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rosberry.notificationservice.R
import com.rosberry.notificationservice.extension.contentToString
import com.rosberry.notificationservice.extension.smoothScrollBottom
import com.rosberry.notificationservice.util.Throttler
import com.rosberry.notificationservice.util.setThrottledOnClickListener
import com.rosberry.notificationservice.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.a_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val throttler = Throttler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        timeRoll.setListeners(
                changeTimeListener = { tag, h, m -> viewModel.changeItemTime(tag, h, m) },
                buttonClickListener = { viewModel.clickItemButton(it) }
        )

        scheduleBtn.setThrottledOnClickListener(throttler) { viewModel.clickSchedule() }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        viewModel.timeRollItems.observe(this, Observer {
            Timber.d("initViewModel::on items: ${it!!.contentToString()}")
            timeRoll.updateItems(it) { nestedScrollView.smoothScrollBottom(250) }
        })
    }
}
