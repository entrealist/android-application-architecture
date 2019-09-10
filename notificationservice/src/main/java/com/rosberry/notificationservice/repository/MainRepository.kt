package com.rosberry.notificationservice.repository

import com.rosberry.notificationservice.extension.dropSeconds
import org.threeten.bp.LocalDateTime


/**
 * @author mmikhailov on 2019-09-10.
 */
object MainRepository {

    fun getInitialTimetable() = mapOf(0 to LocalDateTime.now().dropSeconds())
}