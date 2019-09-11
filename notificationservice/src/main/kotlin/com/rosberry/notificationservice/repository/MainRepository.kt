package com.rosberry.notificationservice.repository

import com.rosberry.notificationservice.extension.contentToString
import com.rosberry.notificationservice.extension.dropSeconds
import com.rosberry.notificationservice.extension.parseLocalDateTime
import com.rosberry.notificationservice.extension.toFormattedString
import com.rosberry.notificationservice.manager.preference.UserPrefs
import org.threeten.bp.LocalDateTime
import timber.log.Timber

/**
 * @author mmikhailov on 2019-09-10.
 */
class MainRepository(private val userPrefs: UserPrefs) {

    fun getTimetable(): Map<Int, LocalDateTime> {
        return userPrefs.timetable
            .asSequence()
            .map { it.parseLocalDateTime() }
            .sorted()
            .mapIndexed { idx, value -> idx to value }
            .associate { it }
            .ifEmpty { mapOf(0 to LocalDateTime.now().dropSeconds()) }
            .also { Timber.d("getTimetable::${it.contentToString()}") }
    }

    fun saveTimetable(value: Map<Int, LocalDateTime>) {
        Timber.d("saveTimetable::${value.contentToString()}")
        userPrefs.timetable = value.asIterable()
            .map { it.value.toFormattedString() }
            .toSet()
    }
}