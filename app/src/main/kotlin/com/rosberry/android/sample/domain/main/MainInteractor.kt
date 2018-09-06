package com.rosberry.android.sample.domain.main

import com.rosberry.android.sample.data.persistence.internal.ResourcesRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(
        ) {

    fun isTrue(b: Boolean): Boolean {
        return b
    }

}