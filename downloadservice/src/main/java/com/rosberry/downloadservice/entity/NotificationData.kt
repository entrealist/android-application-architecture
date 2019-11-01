package com.rosberry.downloadservice.entity

import java.io.Serializable


/**
 * @author neestell on 2019-10-04.
 */
class NotificationData(
        val id: Int = ONGOING_NOTIFICATION_ID,
        val action: String,
        val channelId: String,
        val icon: Int,
        val title: String,
        val desc: String
) : Serializable {

    companion object {
        const val ONGOING_NOTIFICATION_ID = 100
    }
}