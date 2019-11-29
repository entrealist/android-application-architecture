/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.downloadservice.system

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.rosberry.downloadservice.BuildConfig
import com.rosberry.downloadservice.MainActivity
import com.rosberry.downloadservice.entity.NotificationData

/**
 * @author neestell on 2019-08-27.
 */
class DownloadService : Service() {

    companion object {
        private var isRunning: Boolean = false

        const val TAG = "DOW_SERVICE"
        const val ACTION_DOWNLOAD = BuildConfig.APPLICATION_ID + ".service.download"
        const val ACTION_UPLOAD = BuildConfig.APPLICATION_ID + ".service.upload"
        const val ACTION_STOP = BuildConfig.APPLICATION_ID + ".service.stop"
        const val ACTION_PREFIX = BuildConfig.APPLICATION_ID + ".service"

        private const val KEY_NOTIFICATION_DATA = "notification_data"

        fun start(context: Context, notification: NotificationData) {
            val intent = Intent(context, DownloadService::class.java).apply {
                putExtra(KEY_NOTIFICATION_DATA, notification)
                action = notification.action
            }
            ContextCompat.startForegroundService(context, intent)
        }

        fun stop(context: Context, notification: NotificationData? = null) {
            if (isRunning)
                if (notification == null) {
                    context.stopService(Intent(context, DownloadService::class.java))
                } else {
                    val intent = Intent(context, DownloadService::class.java).apply {
                        putExtra(KEY_NOTIFICATION_DATA, notification)
                        action = notification.action
                    }
                    ContextCompat.startForegroundService(context, intent)
                }
        }
    }

    private lateinit var notificationsManager: NotificationsManager

    override fun onCreate() {
        super.onCreate()
        notificationsManager = NotificationsManager(applicationContext, MainActivity::class.java)
        isRunning = true
    }

    override fun onDestroy() {
        stopForeground()
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.run {
            if (isServiceIntent(intent)) {
                notificationsManager.fetchData(intent)?.run {
                    setState(this)
                }
            }
        }

        return START_STICKY
    }

    private fun setState(data: NotificationData) {
        when (data.action) {
            ACTION_DOWNLOAD, ACTION_UPLOAD -> showNotification(data)
            ACTION_STOP -> stop(applicationContext)
            else -> Log.d(TAG, "Not implemented action: " + data.action)
        }
    }

    private fun isServiceIntent(intent: Intent) = intent.action?.startsWith(ACTION_PREFIX) == true

    private fun showNotification(data: NotificationData) = startForeground(data.id, notificationsManager.build(data))

    private fun stopForeground() = stopForeground(true)

}