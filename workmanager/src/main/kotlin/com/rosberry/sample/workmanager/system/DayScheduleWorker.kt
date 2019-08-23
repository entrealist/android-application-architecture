package com.rosberry.sample.workmanager.system

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rosberry.sample.workmanager.system.Constant.KEY_END_DATE
import com.rosberry.sample.workmanager.system.Constant.KEY_ID
import com.rosberry.sample.workmanager.system.Constant.KEY_TIMETABLE_VALUES
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Arrays

/**
 * @author mmikhailov on 26/03/2019.
 */
class DayScheduleWorker(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val tag = "Dbg.DayScheduleWorker"

    override fun doWork(): Result {
        val productId = inputData.getLong(KEY_ID, -1L)
        val timetableValues = inputData.getStringArray(KEY_TIMETABLE_VALUES)
        val endDateString: String? = inputData.getString(KEY_END_DATE)

        if (productId < 0 || timetableValues == null) {
            Log.d(tag, "doWork::Illegal input. Finish with failure.")
            return Result.failure()
        }

        val nowDateTime = LocalDateTime.now()

        if (isAfterEndDate(nowDateTime.toLocalDate(), endDateString)) {
            // do not schedule for day after end date and cancel periodic job
            Log.d(tag, "doWork::the day is after end date. Finish with failure.")
            return Result.failure()
        }

        if (timetableValues.isEmpty()) {
            Log.d(tag, "doWork::timetable is empty. Finish with success.")
            return Result.success()
        }

        Log.d(tag, "doWork::scheduling reminders for day: id: $productId, " +
                "timetable: ${Arrays.toString(timetableValues)}, " +
                "current time is: $nowDateTime")

        ReminderUtil.scheduleDayTimetable(productId, nowDateTime.toLocalTime(), timetableValues)

        return Result.success()
    }

    private fun isAfterEndDate(nowDate: LocalDate, endDateString: String?): Boolean {
        if (endDateString == null) return false // there is no end of periodic work === ONGOING_WAY

        val endDate = DateTimeFormatter.ISO_LOCAL_DATE.parse(endDateString, LocalDate::from)

        return nowDate > endDate
    }
}