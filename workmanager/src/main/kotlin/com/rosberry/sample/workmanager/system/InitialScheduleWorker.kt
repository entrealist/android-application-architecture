package com.rosberry.sample.workmanager.system

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rosberry.sample.workmanager.system.Constant.KEY_END_DATE
import com.rosberry.sample.workmanager.system.Constant.KEY_ID
import com.rosberry.sample.workmanager.system.Constant.KEY_PERIOD_DAYS
import com.rosberry.sample.workmanager.system.Constant.KEY_TIMETABLE_VALUES
import java.util.concurrent.TimeUnit

/**
 * @author mmikhailov on 26/03/2019.
 */
class InitialScheduleWorker(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val tag = "Dbg.InitScheduleWorker"

    override fun doWork(): Result {
        val productUID = inputData.getLong(KEY_ID, -1L)
        val timetableValues = inputData.getStringArray(KEY_TIMETABLE_VALUES)
        val endDateString: String? = inputData.getString(KEY_END_DATE)
        val periodDays = inputData.getLong(KEY_PERIOD_DAYS, -1)

        if (productUID < 0 || periodDays < 0 || timetableValues == null || timetableValues.isEmpty()) {
            return Result.failure()
        }

        val workTag = ReminderUtil.daySchedulerWorkTag(productUID)

        val reminderData = Data.Builder()
            .putLong(KEY_ID, productUID)
            .putStringArray(KEY_TIMETABLE_VALUES, timetableValues)
            .putString(KEY_END_DATE, endDateString)
            .build()

        val dayScheduleWorkRequest = PeriodicWorkRequestBuilder<DayScheduleWorker>(periodDays, TimeUnit.DAYS)
            .setInputData(reminderData)
            .build()

        Log.d(tag,
                "doWork::scheduling periodic work; " +
                        "period: $periodDays days, " +
                        "end date: $endDateString")

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.REPLACE, dayScheduleWorkRequest)

        return Result.success()
    }
}