package com.rosberry.pagination.system

import android.view.View

/**
 * @author Alexei Korshun on 02/10/2018.
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}