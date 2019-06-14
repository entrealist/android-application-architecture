package com.rosberry.sample.rxsearch.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.rosberry.sample.rxsearch.di.AndroidInjector
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * @author mmikhailov on 17/01/2019.
 */
class NetworkConnectionManager {

    companion object {
        fun listenConnectionAvailability(): Observable<Boolean> {
            val context = AndroidInjector.appComponent.context()
            lateinit var receiver: BroadcastReceiver
            return Observable.create<Boolean> { subscriber ->
                receiver = InternetConnectionChangeReceiver {
                    subscriber.onNext(it)
                }
                context.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
                .doOnTerminate { context.unregisterReceiver(receiver) }
                .doOnDispose { context.unregisterReceiver(receiver) }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .filter { it }
        }

        fun isOnline(): Boolean {
            val context = AndroidInjector.appComponent.context()
            val activeNetwork = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo
            return activeNetwork != null
                    && (activeNetwork.type == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
    }
}

private class InternetConnectionChangeReceiver(
        private val listener: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        listener.invoke(NetworkConnectionManager.isOnline() && !isInitialStickyBroadcast)
    }
}
