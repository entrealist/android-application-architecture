package com.rosberry.notificationservice.extension

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author mmikhailov on 14/06/2019.
 */

fun String.getUniqueInteger(): Int {
    val hash = hashCode()
    val m: MessageDigest

    try {
        m = MessageDigest.getInstance("MD5")
        m.reset()
        m.update(toByteArray())
        val digest = m.digest()
        val bigInt = BigInteger(1, digest)
        var hashtext = bigInt.toString(10)
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        var temp = 0
        for (element in hashtext) {
            temp += element.toInt()
        }

        return hash + temp

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return hash
}

inline fun <reified E> Collection<E>.contentToString(): String {
    return toTypedArray().contentToString()
}

inline fun <reified K, reified V> Map<K, V>.contentToString(): String {
    return toList().contentToString()
}

fun is21orAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun is23orAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
fun is24orAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
fun is26orAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
fun is28orAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

@TargetApi(Build.VERSION_CODES.O)
fun Context.createNotificationChannel(id: String, name: String, description: String,
                                      importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
    if (is26orAbove()) {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                NotificationChannel(id, name, importance)
                    .apply { setDescription(description) }
        )
    }
}