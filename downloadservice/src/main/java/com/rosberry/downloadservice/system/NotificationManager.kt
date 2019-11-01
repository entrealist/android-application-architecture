/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.downloadservice.system

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

import com.rosberry.downloadservice.R
import com.rosberry.downloadservice.entity.NotificationData


/**
 * @author neestell on 2019-08-27.
 */
class NotificationsManager(
        private val context: Context,
        private val launchActivity: Class<*>
) {
    companion object {
        private const val KEY_NOTIFICATION_DATA = "notification_data"
    }

    fun build(data: NotificationData): Notification {

        val starterIntent = Intent(context, launchActivity).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val starterPendingIntent: PendingIntent = PendingIntent.getActivity(
                context, 0,
                starterIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val builder = NotificationCompat.Builder(context, data.channelId)
            .setSmallIcon(data.icon)
            .setOnlyAlertOnce(true)
            .setContentTitle(data.title)
            .setContentText(data.desc)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(starterPendingIntent)
            .setOngoing(true)

        return builder.build()
    }

    fun fetchData(intent: Intent): NotificationData? {
        var data: NotificationData? = null

        if (intent.hasExtra(KEY_NOTIFICATION_DATA)) {
            data = intent.getSerializableExtra(KEY_NOTIFICATION_DATA) as NotificationData
        }

        return data
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.download_channel_name)
            val descriptionText = context.getString(R.string.download_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(context.getString(R.string.download_channel_id), name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}