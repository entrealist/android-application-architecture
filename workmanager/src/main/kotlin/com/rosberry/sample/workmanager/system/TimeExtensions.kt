package com.rosberry.sample.workmanager.system

import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

/**
 * @author mmikhailov on 03/04/2019.
 */

fun LocalTime.absoluteHoursAndMinutes(): LocalTime {
    return this.withSecond(0).withNano(0)
}

fun LocalDateTime.absoluteHoursAndMinutes(): LocalDateTime {
    return this.withSecond(0).withNano(0)
}

fun LocalTime.isStartOfDay(): Boolean {
    return hour == 0 && minute == 0
}