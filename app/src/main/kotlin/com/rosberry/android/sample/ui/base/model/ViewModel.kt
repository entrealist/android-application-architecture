package com.rosberry.android.sample.ui.base.model

import android.content.Context
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.v4.content.LocalBroadcastManager
import android.text.InputFilter
import android.util.SparseArray
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.io.Serializable
import java.util.*

/**
 * Created by neestell on 25.09.18.
 */
abstract class ViewModel : Serializable {

    @LayoutRes var layoutId: Int = 0
    @MenuRes var menuId = -1
}
