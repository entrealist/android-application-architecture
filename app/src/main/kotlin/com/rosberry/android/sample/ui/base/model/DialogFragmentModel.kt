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
    var themeId = 0;

    @AnimRes var animationId = 0

    fun setDialogStyle(style: Int, themeId: Int, cancellable: Boolean = false) {
        this.isDialog = true
        this.isCancelable = isCancelable
        this.style = style
        this.themeId = themeId
        this.isCancelable = cancellable
    }


    fun hasOptionMenu() = menuId != 0
}
