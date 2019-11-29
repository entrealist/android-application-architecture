package com.rosberry.downloadservice.data

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.TimeUnit


/**
 * @author neestell on 2019-10-10.
 */
class ApiClient() {

    companion object {
        val MULTIPART_FORM_DATA = "multipart/form-data"
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .apply {
            addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            addInterceptor(CurlLoggingInterceptor())
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://httpbin.org")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    private val api: ServerApi = retrofit.create(ServerApi::class.java)

    fun uploadFile(file: File): Completable {

        val fileBody = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file)
        val filePart = MultipartBody.Part.createFormData("upload", file.name, fileBody)
        val fileDescription = RequestBody.create(MediaType.parse("text/plain"), "This is photo")

        return api.uploadFile(filePart, fileDescription)
    }

    fun downloadFile(file: File): Completable {
        return api.downloadFile()
            .map { response -> responseToFile(file, response) }
            .delay(2, TimeUnit.SECONDS)
            .ignoreElement()
    }

    private fun responseToFile(targetFile: File, body: ResponseBody): File {
        var exception: Exception?

        try {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(targetFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    Log.d("ApiClient", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream.flush()

                return targetFile
            } catch (e: Exception) {
                e.printStackTrace()
                exception = e
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            exception = e
        }

        targetFile.delete()

        throw exception!!
    }
}