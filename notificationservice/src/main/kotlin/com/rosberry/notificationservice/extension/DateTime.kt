package com.rosberry.notificationservice.extension

import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author mmikhailov on 2019-09-10.
 */
fun LocalDateTime.dropSeconds(): LocalDateTime =
        LocalDateTime.of(toLocalDate(), toLocalTime().dropSeconds())

fun LocalTime.dropSeconds(): LocalTime = LocalTime.of(hour, minute)

fun LocalTime.isStartOfDay() = this == LocalTime.MIDNIGHT

fun LocalDateTime.toFormattedString(
        formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
): String = format(formatter)

fun LocalDateTime.toFormattedString(pattern: String): String =
        toFormattedString(DateTimeFormatter.ofPattern(pattern))

fun LocalTime.toFormattedString(
        formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
): String = format(formatter)

fun LocalTime.toFormattedString(pattern: String): String =
        toFormattedString(DateTimeFormatter.ofPattern(pattern))

fun String.parseLocalTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME): LocalTime =
        formatter.parse(this, LocalTime::from)

fun String.parseLocalTime(pattern: String): LocalTime = parseLocalTime(DateTimeFormatter.ofPattern(pattern))

fun String.parseLocalDateTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
        formatter.parse(this, LocalDateTime::from)

fun String.parseLocalDateTime(pattern: String): LocalDateTime = parseLocalDateTime(DateTimeFormatter.ofPattern(pattern))