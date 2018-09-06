package com.rosberry.android.sample.ui.base.model

import android.support.annotation.*

abstract class ToolbarModel: ViewModel() {
    @DrawableRes var backId : Int  = -1
    @IdRes var toolbarId: Int = -1
    @StringRes var title: Int = -1
}