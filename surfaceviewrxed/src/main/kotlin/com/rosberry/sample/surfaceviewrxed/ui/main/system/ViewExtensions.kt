package com.rosberry.sample.surfaceviewrxed.ui.main.system

import android.view.View
import android.view.ViewTreeObserver

/**
 * @author mmikhailov on 10/04/2019.
 */
fun <T : View> T.alsoOnLaid(block: (T) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block.invoke(this@alsoOnLaid)
        }
    })
}