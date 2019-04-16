package com.rosberry.sample.workmanager.data.db

import com.rosberry.sample.workmanager.presentation.ReminderDraft
import javax.inject.Inject

/**
 * @author mmikhailov on 11.03.2019
 */
class ReminderDbConverter @Inject constructor() {

    fun convert(item: ReminderEntity?): ReminderDraft? {
        if (item == null) return null

        return ReminderDraft(
                item.startDate,
                item.endDate,
                item.frequency,
                item.timetable.toMutableList()
        )
    }

    fun wrap(item: ReminderDraft, id: Long): ReminderEntity {
        return ReminderEntity(
                id,
                item.startDate,
                item.endDate,
                item.frequency,
                item.timetable
        )
    }
}