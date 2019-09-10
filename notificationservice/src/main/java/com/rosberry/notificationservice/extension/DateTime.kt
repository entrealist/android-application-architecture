package com.rosberry.notificationservice.extension

import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author mmikhailov on 2019-09-10.
 */
fun LocalDateTime.dropSeconds(): LocalDateTime =
        LocalDateTime.of(toLocalDate(), LocalTime.of(hour, minute))

fun LocalDateTime.formatToLocalTimeString(pattern: String = "HH:mm"): String =
        format(DateTimeFormatter.ofPattern(pattern))