package com.rosberry.android.mediapicker

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.rosberry.mediapicker.MediaPicker
import com.rosberry.mediapicker.data.MediaResult
import com.rosberry.mediapicker.data.PhotoParams
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by dmitry on 02.10.17.
 */

class PhotoPickerFragment : androidx.fragment.app.Fragment(), View.OnClickListener, MediaPicker.OnMediaListener {

    companion object {

        fun newInstance(): androidx.fragment.app.Fragment {
            return PhotoPickerFragment()
        }
    }

    lateinit var contentImageView: ImageView
    lateinit var progressBar: ProgressBar

    lateinit var mediaPicker: MediaPicker
    lateinit var photoParamsOptimized: PhotoParams
    lateinit var photoParamsImmutable: PhotoParams

    lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.f_photo_picker, container, false)

        view.findViewById<View>(R.id.button_photo_pick_gallery)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.button_photo_pick_camera)
            .setOnClickListener(this)
        contentImageView = view.findViewById(R.id.image_photo_content)
        progressBar = view.findViewById(R.id.progress_circle)

        mediaPicker = MediaPicker.from(activity!!)
            .to(this)

        photoParamsOptimized = PhotoParams.Builder()
            .mutable(true)
            .type(MediaPicker.Type.IMAGE)
            .rotate(true)
            .maxSize(768)
            .adjustTextureSize(true)
            .compression(80)
            .pixelFormat(Bitmap.Config.ARGB_8888)
            .noGalleryError("No Gallery")
            .noCameraError("No Camera")
            .takePhotoError("Take photo error")
            .pickGalleryError("Pick gallery error")
            .build()

        photoParamsImmutable = PhotoParams.Builder()
            .type(MediaPicker.Type.IMAGE)
            .mutable(false)
            .build()

        rxPermissions = RxPermissions(this)

        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_photo_pick_gallery -> rxPermissions.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) {
                        mediaPicker.with(photoParamsOptimized)
                            .pick()
                    } else {
                        //.!.
                    }
                }) {}

            R.id.button_photo_pick_camera -> {
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                    .subscribe ({ granted ->
                        if (granted) {
                            mediaPicker.with(photoParamsOptimized)
                                .take()
                        } else {
                            //.!.
                        }
                    },{})
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaPicker.process(requestCode, resultCode, data)
    }

    override fun onPickMediaStateChanged(inProgress: Boolean) {
        progressBar.visibility = if (inProgress) View.VISIBLE else View.INVISIBLE
    }

    override fun onPickMediaResult(result: MediaResult, errorMsg: CharSequence?) {
        Toast.makeText(activity, "Media path: " + result.path, Toast.LENGTH_SHORT)
            .show()

        if (errorMsg == null) {
            contentImageView.setImageBitmap(BitmapFactory.decodeFile(result.path))
        } else {
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
