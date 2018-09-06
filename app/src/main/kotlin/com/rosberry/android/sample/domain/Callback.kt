package com.rosberry.android.sample.domain

interface Callback<in T> {
    fun onResult(value: T)
}
