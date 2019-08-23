package com.rosberry.sample.workmanager.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rosberry.sample.workmanager.domain.Frequency
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

/**
 * @author mmikhailov on 11/03/2019.
 */
@Entity(tableName = "reminder")
data class ReminderEntity(
        @PrimaryKey
        var id: Long,
        var startDate: LocalDate,
        var endDate: LocalDate,
        var frequency: Frequency,
        var timetable: List<LocalTime>
)