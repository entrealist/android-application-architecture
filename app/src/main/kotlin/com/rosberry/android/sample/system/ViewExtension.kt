package com.rosberry.android.sample.system

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.ImageView


/**
 * Created by dmitry on 07.06.17.
 */

fun ViewGroup.inflate(layoutId: Int, attachRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachRoot)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.showPopAnimation() {
    visibility = VISIBLE
    val scaleAnimation = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
    scaleAnimation.fillAfter = true
    scaleAnimation.duration = 300
    startAnimation(scaleAnimation)
}