package com.rosberry.android.sample.ui.base.model

import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import java.io.Serializable

/**
 * Created by neestell on 25.09.18.
 */
abstract class ViewModel : Serializable {

    @LayoutRes var layoutId: Int = 0
    @MenuRes var menuId = -1
}
