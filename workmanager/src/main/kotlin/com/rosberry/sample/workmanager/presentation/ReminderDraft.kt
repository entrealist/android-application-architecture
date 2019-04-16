package com.rosberry.sample.workmanager.presentation

import com.rosberry.sample.workmanager.domain.Frequency
import com.rosberry.sample.workmanager.system.absoluteHoursAndMinutes
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

/**
 * @author mmikhailov on 16/04/2019.
 */
data class ReminderDraft(
        var startDate: LocalDate,
        var endDate: LocalDate,
        var frequency: Frequency,
        var timetable: MutableList<LocalTime>
) {
    companion object {
        fun createBase(): ReminderDraft {
            val dateTime = LocalDateTime.now().absoluteHoursAndMinutes()
            return ReminderDraft(
                    dateTime.toLocalDate(),
                    dateTime.toLocalDate().plusMonths(1),
                    Frequency.EVERYDAY,
                    mutableListOf()
            )
        }
    }
}