package com.rosberry.sample.workmanager.presentation

import com.rosberry.sample.workmanager.domain.Frequency
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

/**
 * @author mmikhailov on 16/04/2019.
 */
interface MainView {
    fun showDatePickerDialog(date: LocalDate, minDate: Long, dateSelectedListener: (Int, Int, Int) -> Unit)
    fun showTimePickerDialog(time: LocalTime, timeSelectedListener: (Int, Int) -> Unit)
    fun setData(startDate: String, endDate: String, frequency: Frequency, timetable: List<String>)
    fun showToast(message: String)
    fun setStartDate(text: String)
    fun setEndDate(text: String)
    fun setTimetable(timetable: List<String>)
}