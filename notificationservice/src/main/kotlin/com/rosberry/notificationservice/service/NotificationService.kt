package com.rosberry.notificationservice.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rosberry.notificationservice.App.Companion.context
import com.rosberry.notificationservice.R
import com.rosberry.notificationservice.extension.contentToString
import com.rosberry.notificationservice.extension.toFormattedString
import com.rosberry.notificationservice.ui.MainActivity
import com.rosberry.notificationservice.util.Constant
import org.threeten.bp.LocalTime
import timber.log.Timber

/**
 * @author mmikhailov on 2019-09-10.
 */
class NotificationService : Service() {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val shownNotifications = mutableListOf<Pair<Int, Notification>>()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Constant.ACTION_TRIGGER_NOTIFICATION -> handleTriggerNotification(intent)
            Constant.ACTION_CANCEL_NOTIFICATION -> handleCancelNotification(intent)
            Constant.ACTION_DISMISS_NOTIFICATION -> handleDismissNotification(intent)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("DefaultLocale")
    private fun handleTriggerNotification(intent: Intent) {
        val notificationId = intent.getIntExtra(Constant.KEY_NOTIFICATION_ID, -1)
        val setTime = intent.getStringExtra(Constant.KEY_REMINDER_SET_TIME)

        val title = notificationId.toString()
        val message = "Set to: $setTime, shown in: ${LocalTime.now().toFormattedString()}"

        val contentIntent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(Constant.KEY_NOTIFICATION_ID, notificationId)
            }
        val contentPendingIntent = PendingIntent.getActivity(
                context,
                notificationId,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val deleteIntent = Intent(context, NotificationService::class.java)
            .apply {
                action = Constant.ACTION_DISMISS_NOTIFICATION
                putExtra(Constant.KEY_NOTIFICATION_ID, notificationId)
            }
        val deletePendingIntent = PendingIntent.getService(
                context,
                notificationId,
                deleteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val icon = R.mipmap.ic_launcher
        val color = ContextCompat.getColor(context, R.color.colorPrimary)
        val notification = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentText(message)
            .setSound(uri)
            .setColor(color)
            .setAutoCancel(false)
            .setContentIntent(contentPendingIntent)
            .setDeleteIntent(deletePendingIntent)
            .build()

        showNotification(notificationId, notification)
    }

    private fun handleCancelNotification(intent: Intent) {
        intent.getIntExtra(Constant.KEY_NOTIFICATION_ID, Constant.NO_NOTIFICATION)
            .takeIf { it != Constant.NO_NOTIFICATION }
            ?.apply { cancelNotification(this) }
    }

    private fun handleDismissNotification(intent: Intent) {
        intent.getIntExtra(Constant.KEY_NOTIFICATION_ID, Constant.NO_NOTIFICATION)
            .takeIf { it != Constant.NO_NOTIFICATION }
            ?.also { dismissedId ->
                Timber.d("handleDismissNotification::remove dismissed notification: $dismissedId")
                val removedIndex = shownNotifications.indexOfFirst { it.first == dismissedId }
                shownNotifications.removeAt(removedIndex)
            }
    }

    private fun showNotification(notificationId: Int, notification: Notification) {
        if (shownNotifications.isEmpty()) {
            Timber.d("showNotification::first notification. Start foreground. ID: $notificationId")
            startForeground(notificationId, notification)
        } else {
            Timber.d("showNotification::not first notification. ID: $notificationId")
            notificationManager.notify(notificationId, notification)
        }

        shownNotifications.add(notificationId to notification)
    }

    private fun cancelNotification(notificationId: Int) {
        Timber.d("cancelNotification::$notificationId, shown notifications: ${shownNotifications.contentToString()}")

        if (shownNotifications.isEmpty()) return

        val cancellingForeground = shownNotifications.first().first == notificationId // first is foreground
        val removedIndex = shownNotifications.indexOfFirst { it.first == notificationId }
        shownNotifications.removeAt(removedIndex)

        if (cancellingForeground) {
            stopForeground(true)

            if (shownNotifications.isNotEmpty()) {
                val nextNotificationPair = shownNotifications.first()
                Timber.d("cancelNotification::start foreground another notification: ${nextNotificationPair.first}")
                startForeground(nextNotificationPair.first, nextNotificationPair.second)
            }
        } else {
            notificationManager.cancel(notificationId)
        }
    }
}