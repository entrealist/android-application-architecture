package com.rosberry.notificationservice.extension

import androidx.lifecycle.MutableLiveData

/**
 * @author mmikhailov on 2019-08-31.
 */
fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
    return MutableLiveData<T>().apply {
        defaultValue?.also { value = it }
    }
}