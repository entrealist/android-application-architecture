package com.rosberry.sample.workmanager.system

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rosberry.sample.workmanager.R
import com.rosberry.sample.workmanager.system.Constant.KEY_SET_TIME
import com.rosberry.sample.workmanager.ui.MainActivity
import org.threeten.bp.LocalTime

/**
 * @author mmikhailov on 25/03/2019.
 */
class ReminderWorker(
        private val context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val notificationManager by lazy { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun doWork(): Result {
        val message = "Set time: ${inputData.getString(KEY_SET_TIME)}, arrived at ${LocalTime.now()}"
        val notificationId = 777

        triggerNotification(context, notificationId, message)

        return Result.success()
    }

    private fun triggerNotification(context: Context, notificationId: Int, message: String?) {
        val contentIntent = Intent(context, MainActivity::class.java)
            .apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        val contentPendingIntent = PendingIntent.getActivity(context, notificationId, contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val icon = R.drawable.ic_launcher_background
        val color = ContextCompat.getColor(context, R.color.colorPrimary)
        val notification = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setSmallIcon(icon)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentText(message)
            .setSound(uri)
            .setColor(color)
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                    NotificationChannel(Constant.CHANNEL_ID, Constant.CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT)
                        .apply { description = Constant.CHANNEL_DESCRIPTION }
            )
        }

        notificationManager.notify(notificationId, notification)
    }
}