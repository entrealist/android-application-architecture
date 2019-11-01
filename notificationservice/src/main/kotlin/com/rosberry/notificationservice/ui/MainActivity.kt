package com.rosberry.notificationservice.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rosberry.notificationservice.App
import com.rosberry.notificationservice.R
import com.rosberry.notificationservice.extension.smoothScrollBottom
import com.rosberry.notificationservice.service.NotificationService
import com.rosberry.notificationservice.util.Constant
import com.rosberry.notificationservice.util.Throttler
import com.rosberry.notificationservice.util.setThrottledOnClickListener
import com.rosberry.notificationservice.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.a_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val throttler = Throttler()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
        initViews()
        initViewModel()
        handleIntent(intent)
    }

    private fun initViews() {
        timeRoll.setListeners(
                changeTimeListener = { tag, h, m -> viewModel.changeItemTime(tag, h, m) },
                buttonClickListener = { viewModel.clickItemButton(it) }
        )

        scheduleBtn.setThrottledOnClickListener(throttler) {
            viewModel.clickSchedule()
            Toast.makeText(this, "Scheduled!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        viewModel.timeRollItems.observe(this, Observer {
            timeRoll.updateItems(it) { nestedScrollView.smoothScrollBottom(250) }
        })
    }

    private fun handleIntent(intent: Intent?) {
        intent?.getIntExtra(Constant.KEY_NOTIFICATION_ID, Constant.NO_NOTIFICATION)
            ?.takeIf { it != Constant.NO_NOTIFICATION }
            ?.also { id ->
                Timber.d("handleIntent::there is notification to cancel: $id")
                val service = Intent(this@MainActivity, NotificationService::class.java).apply {
                    action = Constant.ACTION_CANCEL_NOTIFICATION
                    putExtra(Constant.KEY_NOTIFICATION_ID, id)
                }

                App.context.startService(service)
            }
    }
}
