package com.rosberry.notificationservice.util

import android.os.Handler
import android.view.View

/**
 * @author mmikhailov on 2019-07-12.
 */
class Throttler(private val thresholdMs: Long = 300) {

    companion object {
        private const val WHAT = 7879
    }

    private val handler: Handler = Handler()

    fun publish(block: () -> Unit) {
        if (handler.hasMessages(WHAT)) {
            return
        }

        block.invoke()
        handler.sendMessageDelayed(handler.obtainMessage(WHAT), thresholdMs)
    }

    fun clear() {
        handler.removeCallbacksAndMessages(null) // null means all
    }
}

fun View.setThrottledOnClickListener(throttler: Throttler, block: () -> Unit) {
    setOnClickListener { throttler.publish(block) }
}