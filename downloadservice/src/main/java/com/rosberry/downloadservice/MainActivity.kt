package com.rosberry.downloadservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rosberry.downloadservice.entity.NotificationData
import com.rosberry.downloadservice.presentation.MainPresenter
import com.rosberry.downloadservice.presentation.MainView
import com.rosberry.downloadservice.system.DownloadService.Companion.ACTION_DOWNLOAD
import com.rosberry.downloadservice.system.DownloadService.Companion.ACTION_UPLOAD
import com.rosberry.downloadservice.system.DownloadService.Companion.start
import com.rosberry.downloadservice.system.DownloadService.Companion.stop
import com.rosberry.downloadservice.system.FileUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy {
        MainPresenter(this, fileUtils = FileUtils(filesDir.absolutePath))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDownload.setOnClickListener { presenter.clickStartDownload() }
        buttonUpload.setOnClickListener { presenter.clickStartUpload() }
        buttonCancel.setOnClickListener { presenter.clickStopLoad() }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun stopLoadService() {
        stop(applicationContext)
    }

    override fun startUploadService() {
        val data = NotificationData(
                action = ACTION_UPLOAD,
                channelId = getString(R.string.download_channel_id),
                icon = android.R.drawable.stat_sys_upload,
                title = getString(R.string.data),
                desc = getString(R.string.uploading_data)
        )
        start(applicationContext, data)
    }

    override fun startDownloadService() {
        val data = NotificationData(
                action = ACTION_DOWNLOAD,
                channelId = getString(R.string.download_channel_id),
                icon = android.R.drawable.stat_sys_download,
                title = getString(R.string.data),
                desc = getString(R.string.downloading_data)
        )
        start(applicationContext, data)
    }
}
