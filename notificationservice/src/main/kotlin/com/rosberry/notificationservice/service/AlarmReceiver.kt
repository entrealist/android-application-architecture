package com.rosberry.notificationservice.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rosberry.notificationservice.extension.is26orAbove
import com.rosberry.notificationservice.extension.toFormattedString
import com.rosberry.notificationservice.util.Scheduler
import org.threeten.bp.LocalTime
import timber.log.Timber

/**
 * @author mmikhailov on 05/04/2019.
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive::intent: $intent")

        val extras = intent.extras
        if (extras == null) {
            Timber.d("onReceive::No extras. Exit.")
            return
        }

        val notificationId = extras.getInt(Scheduler.KEY_NOTIFICATION_ID, -1)
        var setTime = extras.getString(Scheduler.KEY_REMINDER_SET_TIME)

        Timber.d("""
            onReceive::executing timetable item:
                        notification id:        $notificationId,
                        notification time set:  $setTime,
                        the time is now:        ${LocalTime.now()}
                        """.trimIndent())

        if (notificationId == -1) {
            Timber.e("onReceive::Illegal input. Exit.")
            return
        }

        if (setTime == null) {
            setTime = LocalTime.now()
                .toFormattedString()
        }

        val service = Intent(context, NotificationService::class.java).apply {
            action = Scheduler.ACTION_TRIGGER_NOTIFICATION
            putExtra(Scheduler.KEY_NOTIFICATION_ID, notificationId)
            putExtra(Scheduler.KEY_REMINDER_SET_TIME, setTime)
        }

        if (is26orAbove()) {
            context.startForegroundService(service)
        } else {
            context.startService(service)
        }
    }
}