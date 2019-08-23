package com.rosberry.sample.workmanager.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.rosberry.sample.workmanager.di.Injector
import com.rosberry.sample.workmanager.domain.MainInteractor
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TimeChangedBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var mainInteractor: MainInteractor

    private val tag = "Dbg.TimeChangedReceiver"

    init {
        Injector.appComponent.inject(this)
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(tag, "onReceive::${intent.action}, now the time is: ${LocalDateTime.now()}")

        mainInteractor.rescheduleReminder()
    }
}