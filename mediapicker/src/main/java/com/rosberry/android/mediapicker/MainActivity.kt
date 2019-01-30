package com.rosberry.android.mediapicker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showPhotoMode()

        findViewById<RadioGroup>(R.id.radio_group_mode).setOnCheckedChangeListener { radioGroup, i ->

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
