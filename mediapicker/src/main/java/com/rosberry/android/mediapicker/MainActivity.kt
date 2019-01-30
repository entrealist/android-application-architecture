package com.rosberry.android.mediapicker

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        public val REQUEST_CODE_GALLERY = 123
        public val REQUEST_CODE_CAMERA = 124
    }

    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showPhotoMode()

        radioGroup = findViewById(R.id.radio_group_mode)

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->

            when (i) {
                R.id.radio_photo -> showPhotoMode()
                R.id.radio_video -> showVideoMode()
            }

        }
    }

    private fun showVideoMode() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_view, VideoPickerFragment.newInstance())
            .commit()
    }

    private fun showPhotoMode() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_view, PhotoPickerFragment.newInstance())
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager
            .fragments[0]
            .onActivityResult(requestCode, resultCode, data)
    }
}
