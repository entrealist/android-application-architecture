package com.rosberry.sample.rxsearch.domain

import com.rosberry.sample.rxsearch.data.manager.NetworkConnectionManager
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author mmikhailov 21.01.2019
 */
class ConnectivityInteractor @Inject constructor() {

    fun listenConnectionAvailability(): Observable<Boolean> {
        return NetworkConnectionManager.listenConnectionAvailability()
    }
}