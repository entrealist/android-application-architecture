package com.rosberry.downloadservice.presentation

import com.rosberry.downloadservice.data.ApiClient
import com.rosberry.downloadservice.system.FileUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File


/**
 * @author neestell on 2019-10-10.
 */
class MainPresenter(
        private val view: MainView,
        private val apiClient: ApiClient = ApiClient(),
        private val fileUtils: FileUtils
) {
    private val compositeDisposable = CompositeDisposable()

    fun clickStartDownload() {
        apiClient.downloadFile(fileUtils.getEmptyFile())
            .doOnSubscribe { view.startDownloadService() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { view.stopLoadService() }
            .subscribe(
                    {},
                    { error -> error.printStackTrace() }
            )
            .connect()
    }

    fun clickStartUpload() {
        fileUtils.getFileWithSizeInMB(2)
            .doOnSubscribe { view.startUploadService() }
            .subscribeOn(Schedulers.io())
            .map { file -> uploadFile(file) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {},
                    { error -> error.printStackTrace() }
            )
            .connect()

    }

    fun clickStopLoad() {
        compositeDisposable.clear()
        view.stopLoadService()
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    private fun uploadFile(file: File) {
        apiClient.uploadFile(file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { view.stopLoadService() }
            .subscribe({}, {})
            .connect()
    }

    private fun Disposable.connect(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}