package com.rosberry.notificationservice.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.rosberry.notificationservice.App.Companion.context
import com.rosberry.notificationservice.extension.dropSeconds
import com.rosberry.notificationservice.extension.getUniqueInteger
import com.rosberry.notificationservice.extension.isStartOfDay
import com.rosberry.notificationservice.extension.toFormattedString
import com.rosberry.notificationservice.service.AlarmReceiver
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import timber.log.Timber

/**
 * @author mmikhailov on 2019-09-11.
 */
object Scheduler {

    fun scheduleTimetable(nowDateTime: LocalDateTime, timetable: List<LocalTime>) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiver = ComponentName(context, AlarmReceiver::class.java)
        context.packageManager
            .setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)

        for (timetableItemTime in timetable) {
            val delay = calculateDurationMsBetween(nowDateTime.toLocalTime(),
                    timetableItemTime)
            if (delay < 0) continue // past

            val notificationId = getNotificationId(timetableItemTime)
            val intent = Intent(context, AlarmReceiver::class.java)
                .putExtra(Constant.KEY_NOTIFICATION_ID, notificationId)
                .putExtra(Constant.KEY_REMINDER_SET_TIME, timetableItemTime.toFormattedString())

            val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setAlarmClock(
                    AlarmManager.AlarmClockInfo(System.currentTimeMillis() + delay, pendingIntent),
                    pendingIntent
            )

            Timber.d("scheduleTimetable::alarm set on: $timetableItemTime, delay before: ${delay / 1000f / 60f} min")
        }
    }

    private fun calculateDurationMsBetween(nowTime: LocalTime, target: LocalTime): Long {
        return when {
            target.dropSeconds() == nowTime.dropSeconds() && nowTime.isStartOfDay() -> 0 // target == now time == 00:00 (Midnight)
            target.isAfter(nowTime) -> Duration.between(nowTime, target).toMillis()
            else -> -1
        }
    }

    private fun getNotificationId(time: LocalTime): Int {
        return time.toFormattedString()
            .getUniqueInteger()
    }
}