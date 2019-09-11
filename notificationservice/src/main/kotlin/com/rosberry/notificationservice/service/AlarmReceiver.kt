package com.rosberry.notificationservice.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

/**
 * @author mmikhailov on 05/04/2019.
 */
class AlarmReceiver : BroadcastReceiver() {

    /*@Inject
    lateinit var reminderInteractor: ReminderInteractor

    private val tag = "Dbg.ReminderReceiver"

    private var disposable: Disposable? = null

    init {
        AndroidInjector.appComponent.inject(this)
    }*/

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive::intent: $intent")

        val extras = intent.extras
        if (extras == null) {
            Timber.d("onReceive::No extras. Exit.")
            return
        }

        /*val productId = extras.getLong(Constant.KEY_PRODUCT_ID, -1L)
        val notificationId = extras.getInt(Constant.KEY_NOTIFICATION_ID, -1)
        var setTime = extras.getString(Constant.KEY_REMINDER_SET_TIME)

        Log.d(tag,
                "onReceive::executing timetable item: productId: $productId, " +
                        "notificationId: $notificationId, " +
                        "time set: $setTime, " +
                        "the time now is: ${LocalTime.now()}")

        if (productId < 0 || notificationId == -1) {
            Log.e(tag, "onReceive::Illegal input. Exit.")
            return
        }

        if (setTime == null) {
            setTime = LocalTime.now().formatStringISO()
        }

        disposable?.dispose()
        disposable = reminderInteractor.getReminderMessageParts(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val service = Intent(context, ReminderService::class.java).apply {
                    action = Constant.ACTION_TRIGGER_NOTIFICATION
                    putExtra(Constant.KEY_PRODUCT_ID, productId)
                    putExtra(Constant.KEY_NOTIFICATION_ID, notificationId)
                    putExtra(Constant.KEY_REMINDER_SET_TIME, setTime)
                    putExtra(Constant.KEY_DOSAGE, it.first)
                    putExtra(Constant.KEY_PRODUCT_NAME, it.second)
                }

                if (is26orAbove()) {
                    context.startForegroundService(service)
                } else {
                    context.startService(service)
                }

            }, {
                it.printStackTrace()
            })*/
    }
}