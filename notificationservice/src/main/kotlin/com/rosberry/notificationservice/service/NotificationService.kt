package com.rosberry.notificationservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author mmikhailov on 2019-09-10.
 */
class NotificationService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}