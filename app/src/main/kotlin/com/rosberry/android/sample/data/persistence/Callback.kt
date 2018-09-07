package com.rosberry.android.sample.data.persistence

interface Callback<in T> {
    fun onResult(value: T)
}
