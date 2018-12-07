/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */

package com.rosberry.sample.errorhandler.domain

import com.rosberry.sample.errorhandler.R
import com.rosberry.sample.errorhandler.data.system.ResourceManager
import com.rosberry.sample.errorhandler.entity.State
import java.io.IOException
import java.net.UnknownHostException

/**
 * @author Alexei Korshun on 21/11/2018.
 */
class SampleInteractor(
        private val resourceManager: ResourceManager
) {

    fun proceed(state: State): Single<String> {
        return Single.create { emitter ->
            when (state) {
                State.ILLEGAL -> emitter.onError(IllegalStateException())
                State.UNKNOWN_HOST -> emitter.onError(UnknownHostException())
                State.NETWORK_ERROR -> emitter.onError(IOException())
                State.UNKNOWN -> emitter.onError(Exception())
                State.SUCCESS -> emitter.onSuccess(
                        resourceManager.getString(R.string.core_error_handler_success_result))
            }
        }
    }
}