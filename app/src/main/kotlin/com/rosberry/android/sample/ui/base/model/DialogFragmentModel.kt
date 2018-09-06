package com.rosberry.android.sample.ui.base.model

import android.support.annotation.AnimRes

/**
 * Created by dmitry on 30.03.18.
 */

class DialogFragmentModel : FragmentModel() {
    var isDialog = false
    var isCancelable = true
    var isCancelableOutside = false
    var style: Int = 0
    var margin = 0
    var width = -1
    var height = -1

    @AnimRes var animationId = 0

    fun init(isDialog: Boolean, isCancelable: Boolean, style: Int) {
        this.isDialog = isDialog
        this.isCancelable = isCancelable
        this.style = style
    }

    fun size(width: Int, height: Int) {
        this.width = width
        this.height = height
    }
}
