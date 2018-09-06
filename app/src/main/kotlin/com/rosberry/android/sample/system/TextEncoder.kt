package com.rosberry.android.sample.system

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


object TextEncoder {

    val TRANSFORMATION_MODE_2 = "AES/ECB/PKCS7Padding"
    @Throws(Exception::class)
    private fun encrypt(str: String, key: Key): String {
        val c = Cipher.getInstance(TRANSFORMATION_MODE_2)
        c.init(Cipher.ENCRYPT_MODE, key)
        val encVal = c.doFinal(str.toByteArray(charset("UTF-8")))
        val encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT)

        return encryptedValue
    }

    @Throws(Exception::class)
    private fun decrypt(encStr: String, key: Key): String {
        val c = Cipher.getInstance(TRANSFORMATION_MODE_2)
        c.init(Cipher.DECRYPT_MODE, key)

        val decordedValue = Base64.decode(encStr, Base64.DEFAULT)
        val decValue = c.doFinal(decordedValue)
        val decryptedValue = String(decValue)

        return decryptedValue
    }

    private fun getKet(secret: String): Key {
        var key: Key? = null
        var correctSecret = ""
        if (secret.length >= 16) {
            correctSecret = secret.substring(0, 16)
        } else {
            for (i in 0..16 - secret.length - 1) {
                if (i == 0) {
                    correctSecret = correctSecret + secret
                }
                correctSecret = correctSecret + "x"
            }
        }
        try {
            key = SecretKeySpec(correctSecret.toByteArray(charset("UTF-8")), "AES")
        } catch (e: UnsupportedEncodingException) {
            // Crashlytics.logException(e)
            e.printStackTrace()
        }

        if (key == null) {
            val bytes = byteArrayOf()
            key = SecretKeySpec(bytes, "AES")
        }
        return key
    }

    fun encrypt(value: String, secret: String): String {
        var result = "" + value
        try {
            result = encrypt(value, getKet(secret))
        } catch (e: Exception) {
            //Crashlytics.logException(e)
            e.printStackTrace()
        }

        return result
    }

    fun decrypt(encryptedValue: String, secret: String): String {
        var result = ""
        try {
            result = decrypt(encryptedValue, getKet(secret))
        } catch (e: Exception) {
            e.printStackTrace()
            // Crashlytics.logException(e)

        }

        return result
    }
}