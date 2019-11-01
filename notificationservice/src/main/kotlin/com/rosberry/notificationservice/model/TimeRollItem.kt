package com.rosberry.notificationservice.model

/**
 * @author mmikhailov on 2019-09-10.
 */
data class TimeRollItem(
        val id: Int,
        val textValue: String,
        val hour: Int,
        val minute: Int
)