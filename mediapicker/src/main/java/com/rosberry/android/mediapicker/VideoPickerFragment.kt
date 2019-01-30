package com.rosberry.android.mediapicker

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*
import com.rosberry.mediapicker.MediaPicker
import com.rosberry.mediapicker.data.MediaResult
import com.rosberry.mediapicker.data.PhotoParams
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * Created by dmitry on 02.10.17.
 */

class VideoPickerFragment : Fragment(), View.OnClickListener, MediaPicker.OnMediaListener {

    private var mediaPicker: MediaPicker? = null

    lateinit var videoView: VideoView
    lateinit var progressBar: ProgressBar

    lateinit var params: PhotoParams

    lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.f_video_picker, container, false)

        view.findViewById<View>(R.id.button_video_pick_gallery)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.button_video_pick_camera)
            .setOnClickListener(this)
        videoView = view.findViewById(R.id.video_content)
        progressBar = view.findViewById(R.id.progress_circle)

        mediaPicker = MediaPicker.from(activity!!)
            .to(this)

        params = PhotoParams.Builder()
            .type(MediaPicker.Type.VIDEO)
            .duration(5)
            .facing(false)
            .highQuality(true)
            .build()

        rxPermissions = RxPermissions(this)

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaPicker!!.process(requestCode, resultCode, data)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_video_pick_gallery -> {
                rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            mediaPicker!!.with(params)
                                .pick()
                        } else {
                            //.!.
                        }
                    }
            }

            R.id.button_video_pick_camera -> {
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                            mediaPicker!!.with(params)
                                .take()
                        } else {
                            //.!.
                        }
                    }
            }
        }
    }

    override fun onPickMediaStateChanged(inProgress: Boolean) {
        progressBar.visibility = if (inProgress) View.VISIBLE else View.INVISIBLE
    }

    override fun onPickMediaResult(result: MediaResult, errorMsg: CharSequence?) {
        Toast.makeText(activity, "Media path: " + result.path, Toast.LENGTH_SHORT)
            .show()

        if (errorMsg == null) {
            videoView.setVideoPath(result.path)
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false
                mediaPlayer.start()
            }
        } else {
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {

        fun newInstance(): Fragment {
            return VideoPickerFragment()
        }
    }
}
