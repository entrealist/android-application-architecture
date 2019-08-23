package com.rosberry.sample.workmanager.presentation

import com.rosberry.sample.workmanager.domain.Frequency
import com.rosberry.sample.workmanager.domain.MainInteractor
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

/**
 * @author mmikhailov on 16/04/2019.
 */
class MainPresenter @Inject constructor(
        private val mainInteractor: MainInteractor
) {

    private val dateFormat = DateTimeFormatter.ofPattern("dd.M.yyyy")
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    private lateinit var view: MainView

    fun onCreate(view: MainView) {
        this.view = view

        setupScreen()
    }

    fun clickStartDate() {
        val initDate = mainInteractor.getStartDateValue()
        val minDateMillis = 0L
        view.showDatePickerDialog(initDate, minDateMillis) { y, m, d ->
            mainInteractor.changeStartDate(LocalDate.of(y, m + 1, d))
            view.setStartDate(mainInteractor.getStartDateValue().format(dateFormat))

            if (mainInteractor.getStartDateValue() > mainInteractor.getEndDateValue()) {
                mainInteractor.resetEndDate()
                view.setEndDate(mainInteractor.getEndDateValue().format(dateFormat))
            }
        }
    }

    fun clickEndDate() {
        val initDate = mainInteractor.getEndDateValue()
        val minDateMillis = mainInteractor.getStartDateValue().atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli()
        view.showDatePickerDialog(initDate, minDateMillis) { y, m, d ->
            mainInteractor.changeEndDate(LocalDate.of(y, m + 1, d))
            view.setEndDate(mainInteractor.getEndDateValue().format(dateFormat))
        }
    }

    fun check(frequency: Frequency) {
        mainInteractor.changeFrequency(frequency)
    }

    fun clickAddTime() {
        val time = LocalTime.now()
        view.showTimePickerDialog(time) { h, m ->
            mainInteractor.addTimetableEntry(LocalTime.of(h, m))
            view.setTimetable(mainInteractor.getTimeTable().map { it.format(timeFormat) })
        }
    }

    fun clickClear() {
        mainInteractor.clearTimetable()
        view.setTimetable(emptyList())
    }

    fun clickSave() {
        mainInteractor.saveReminder()
        view.showToast("Changes applied")
    }

    private fun setupScreen() {
        val reminderDraft = mainInteractor.getReminder(mainInteractor.reminderId)
        val nowDate = reminderDraft.startDate.format(dateFormat)
        val endDate = reminderDraft.endDate.format(dateFormat)
        val frequency = reminderDraft.frequency
        val timetable = reminderDraft.timetable
            .map { it.format(timeFormat) }
            .toList()

        view.setData(nowDate, endDate, frequency, timetable)
    }
}
