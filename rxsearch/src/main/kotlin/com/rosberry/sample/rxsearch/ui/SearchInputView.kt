package com.rosberry.sample.rxsearch.ui

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View.OnKeyListener

class SearchInputView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyle) {

    private var mSearchKeyListener: OnKeyboardSearchKeyClickListener? = null
    private var mOnKeyboardDismissedListener: OnKeyboardDismissedListener? = null

    init {
        setOnKeyListener(OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                mSearchKeyListener?.onSearchKeyClicked()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onKeyPreIme(keyCode: Int, ev: KeyEvent): Boolean {
        return if (ev.keyCode == KeyEvent.KEYCODE_BACK &&
                mOnKeyboardDismissedListener != null &&
                mOnKeyboardDismissedListener!!.onKeyboardDismissed()) {
            true
        } else {
            super.onKeyPreIme(keyCode, ev)
        }
    }

    fun setOnKeyboardDismissedListener(onKeyboardDismissedListener: OnKeyboardDismissedListener) {
        mOnKeyboardDismissedListener = onKeyboardDismissedListener
    }

    fun setOnSearchKeyListener(searchKeyListener: OnKeyboardSearchKeyClickListener) {
        mSearchKeyListener = searchKeyListener
    }

    interface OnKeyboardDismissedListener {
        fun onKeyboardDismissed(): Boolean
    }

    interface OnKeyboardSearchKeyClickListener {
        fun onSearchKeyClicked()
    }
}