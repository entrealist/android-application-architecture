package com.rosberry.notificationservice.extension

import android.view.View
import android.view.ViewTreeObserver
import androidx.core.widget.NestedScrollView

/**
 * @author mmikhailov on 2019-09-10.
 */
fun <T : View> T.alsoOnLaid(block: (T) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block.invoke(this@alsoOnLaid)
        }
    })
}

fun NestedScrollView.smoothScrollBottom(delay: Long) {
    this.postDelayed({
        val scrollViewHeight = height
        if (scrollViewHeight > 0) {
            val lastView = getChildAt(childCount - 1)
            val lastViewBottom = lastView.bottom + paddingBottom
            val deltaScrollY = lastViewBottom - scrollViewHeight - scrollY
            smoothScrollBy(0, deltaScrollY)
        }
    }, delay)
}