package com.rosberry.downloadservice.system

import io.reactivex.Single
import java.io.File
import java.io.FileWriter


/**
 * @author neestell on 2019-10-10.
 */
class FileUtils(private val cacheRootDir: String) {
    companion object {
        const val TEMPORARY_FILE_NAME = "raw_temp_file.data"
    }

    fun getEmptyFile(): File = File(cacheRootDir, TEMPORARY_FILE_NAME)

    fun getFileWithSizeInMB(size: Int): Single<File> {
        return Single.fromCallable {
            val file = getEmptyFile()
            FileWriter(file).run {
                val stubData = "00000000000"
                var countWritten = 0
                while (countWritten <= size * 1024 * 102) {
                    append(stubData)
                    countWritten++
                }
                flush()
                close()
            }
            return@fromCallable file
        }
    }
}