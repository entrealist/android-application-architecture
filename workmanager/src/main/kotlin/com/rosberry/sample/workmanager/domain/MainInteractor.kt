package com.rosberry.sample.workmanager.domain

import android.util.Log
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rosberry.sample.workmanager.data.db.AppDatabase
import com.rosberry.sample.workmanager.data.db.ReminderDbConverter
import com.rosberry.sample.workmanager.presentation.ReminderDraft
import com.rosberry.sample.workmanager.system.Constant.KEY_END_DATE
import com.rosberry.sample.workmanager.system.Constant.KEY_ID
import com.rosberry.sample.workmanager.system.Constant.KEY_PERIOD_DAYS
import com.rosberry.sample.workmanager.system.Constant.KEY_TIMETABLE_VALUES
import com.rosberry.sample.workmanager.system.InitialScheduleWorker
import com.rosberry.sample.workmanager.system.ReminderUtil
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author mmikhailov on 16/04/2019.
 */
@Singleton
class MainInteractor @Inject constructor(
        private val db: AppDatabase,
        private val reminderDbConverter: ReminderDbConverter
) {

    companion object {
        private const val TAG = "Dbg.MainInteractor"
        private val DAY_SCHEDULE_TIME = LocalTime.MIDNIGHT
    }

    private lateinit var reminderDraft: ReminderDraft
    val reminderId = 999L // the only reminder in this sample app

    fun getReminder(reminderId: Long): ReminderDraft {
        return (reminderDbConverter.convert(db.reminderDao().findReminderById(reminderId))
            ?: ReminderDraft.createBase())
            .also { reminderDraft = it }
    }

    fun getStartDateValue(): LocalDate {
        return reminderDraft.startDate
    }

    fun getEndDateValue(): LocalDate {
        return reminderDraft.endDate
    }

    fun changeStartDate(date: LocalDate) {
        reminderDraft.startDate = date
    }

    fun changeEndDate(date: LocalDate) {
        reminderDraft.endDate = date
    }

    fun resetEndDate() {
        reminderDraft.endDate = reminderDraft.startDate.plusMonths(1)
    }

    fun changeFrequency(frequency: Frequency) {
        reminderDraft.frequency = frequency
    }

    fun addTimetableEntry(time: LocalTime) {
        reminderDraft.timetable.add(time)
    }

    fun clearTimetable() {
        reminderDraft.timetable.clear()
    }

    fun saveReminder() {
        val entity = reminderDbConverter.wrap(reminderDraft, reminderId)
        db.reminderDao()
            .insert(entity)

        if (reminderDraft.timetable.isNotEmpty()) {
            scheduleReminder(reminderId, reminderDraft)
        } else {
            cancelScheduledReminder()
        }
    }

    fun getTimeTable(): List<LocalTime> {
        return reminderDraft.timetable
    }

    //region scheduling area

    private fun cancelScheduledReminder() {
        Log.d(TAG, "cancelScheduledReminder::")

        WorkManager.getInstance()
            .apply {
                cancelUniqueWork(ReminderUtil.initialWorkTag(reminderId))
                cancelUniqueWork(ReminderUtil.daySchedulerWorkTag(reminderId))
                cancelUniqueWork(ReminderUtil.dayReminderWorkChainTag(reminderId))
            }
    }

    fun rescheduleReminder() {
        Log.d(TAG, "rescheduleReminder::")

        cancelScheduledReminder()
        val reminderDraft = getReminder(reminderId)
        scheduleReminder(reminderId, reminderDraft)
    }

    private fun scheduleReminder(id: Long, reminderDraft: ReminderDraft) {
        val nowDateTime = LocalDateTime.now()

        if (nowDateTime.toLocalDate().isAfter(reminderDraft.endDate)) {
            Log.d(TAG, "scheduleReminder::nothing to schedule. Exit")
            return
        }

        val endDateString: String = reminderDraft.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val workTag = ReminderUtil.initialWorkTag(id)
        val desiredStartDate = reminderDraft.startDate
        val period = reminderDraft.frequency.periodInDays
        val timetableValues = reminderDraft.timetable
            .map { it.format(DateTimeFormatter.ISO_LOCAL_TIME) }
            .toTypedArray()

        if (shouldScheduleCurrentDay(nowDateTime.toLocalDate(), desiredStartDate, period)) {
            Log.d(TAG, "scheduleReminder::scheduling timetable for current day")
            ReminderUtil.scheduleDayTimetable(id, nowDateTime.toLocalTime(), timetableValues)
        }

        val initDelay = calculateDelayBeforeStartPeriodicWork(nowDateTime, desiredStartDate, period)

        Log.d(TAG, "scheduleReminder::scheduling initial schedule for id: $id, " +
                "delay before starting day: $initDelay = ${initDelay / 1000f / 60f / 60f} h.")

        scheduleInitialScheduler(id, timetableValues, period, endDateString, initDelay, workTag)
    }

    private fun scheduleInitialScheduler(
            id: Long,
            timetableValues: Array<String>,
            period: Long,
            endDateString: String?,
            initDelay: Long,
            workTag: String
    ) {

        val reminderData = Data.Builder()
            .putLong(KEY_ID, id)
            .putStringArray(KEY_TIMETABLE_VALUES, timetableValues)
            .putLong(KEY_PERIOD_DAYS, period)
            .putString(KEY_END_DATE, endDateString)
            .build()

        val initScheduleWorkRequest = OneTimeWorkRequestBuilder<InitialScheduleWorker>()
            .setInitialDelay(initDelay, TimeUnit.MILLISECONDS)
            .setInputData(reminderData)
            .build()

        WorkManager.getInstance()
            .enqueueUniqueWork(workTag, ExistingWorkPolicy.REPLACE, initScheduleWorkRequest)
    }

    private fun shouldScheduleCurrentDay(
            nowDate: LocalDate,
            desiredStartDate: LocalDate,
            periodDays: Long
    ): Boolean {

        return when {
            nowDate.isEqual(desiredStartDate) -> true
            nowDate.isAfter(desiredStartDate) -> {
                val diffDays = desiredStartDate.until(nowDate, ChronoUnit.DAYS)
                diffDays % periodDays == 0L
            }
            else -> false
        }
    }

    private fun calculateDelayBeforeStartPeriodicWork(
            nowDateTime: LocalDateTime,
            desiredStartDate: LocalDate,
            periodDays: Long
    ): Long {

        val nowDate = nowDateTime.toLocalDate()
        val targetDate = when {
            nowDate.isEqual(desiredStartDate) -> desiredStartDate.plusDays(periodDays)
            nowDate.isAfter(desiredStartDate) -> {
                val diffDays = desiredStartDate.until(nowDate, ChronoUnit.DAYS)
                val daysToAdd = periodDays - (diffDays % periodDays)
                nowDate.plusDays(daysToAdd)
            }
            else -> desiredStartDate
        }

        val targetDateTime = targetDate.atTime(DAY_SCHEDULE_TIME)

        Log.d(TAG, "calculateDelayBeforeStartPeriodicWork::" +
                "calculating diff between now: $nowDateTime and corrected date: $targetDateTime, " +
                "desired start date was: $desiredStartDate")

        return Duration.between(nowDateTime, targetDateTime)
            .toMillis()
    }

    //endregion
}