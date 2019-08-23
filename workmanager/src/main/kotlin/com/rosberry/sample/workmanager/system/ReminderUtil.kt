package com.rosberry.sample.workmanager.system

import android.util.Log
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rosberry.sample.workmanager.system.Constant.POSTFIX_DAY_SCHEDULING_WORK_TAG
import com.rosberry.sample.workmanager.system.Constant.POSTFIX_INITIAL_WORK_TAG
import com.rosberry.sample.workmanager.system.Constant.POSTFIX_REMINDER_WORK_CHAIN_TAG
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

/**
 * @author mmikhailov on 02/04/2019.
 */
object ReminderUtil {

    fun initialWorkTag(id: Long) = "$id$POSTFIX_INITIAL_WORK_TAG"
    fun daySchedulerWorkTag(id: Long) = "$id$POSTFIX_DAY_SCHEDULING_WORK_TAG"
    fun dayReminderWorkChainTag(id: Long) = "$id$POSTFIX_REMINDER_WORK_CHAIN_TAG"

    fun scheduleDayTimetable(
            productUID: Long,
            nowTime: LocalTime,
            timetableValues: Array<String>
    ) {

        val reminderWorkRequestList = mutableListOf<OneTimeWorkRequest>()
        for (timetableEntry in timetableValues) {
            val itemTime = DateTimeFormatter.ISO_LOCAL_TIME.parse(timetableEntry, LocalTime::from)

            val delay = calculateDelayBeforeTime(nowTime, itemTime)
            if (delay < 0) continue

            val reminderDataBuilder = Data.Builder()
                .putLong(Constant.KEY_ID, productUID)
                .putString(Constant.KEY_SET_TIME, timetableEntry)

            Log.d("Dbg.ReminderUtil", "scheduleDayTimetable::add reminder request for time: $itemTime, " +
                    "delay before: ${delay / 1000f / 60f} min")

            reminderWorkRequestList.add(
                    OneTimeWorkRequestBuilder<ReminderWorker>()
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .setInputData(reminderDataBuilder.build())
                        .build()
            )
        }

        if (reminderWorkRequestList.isNotEmpty()) {
            val chainWorkTag = dayReminderWorkChainTag(productUID)

            WorkManager.getInstance()
                .beginUniqueWork(chainWorkTag, ExistingWorkPolicy.REPLACE, reminderWorkRequestList)
                .enqueue()
        }
    }

    private fun calculateDelayBeforeTime(nowTime: LocalTime, target: LocalTime): Long {
        return when {
            target.absoluteHoursAndMinutes() == nowTime.absoluteHoursAndMinutes() && nowTime.isStartOfDay() -> 0
            target.isAfter(nowTime) -> Duration.between(nowTime, target).toMillis()
            else -> -1
        }
    }
}
