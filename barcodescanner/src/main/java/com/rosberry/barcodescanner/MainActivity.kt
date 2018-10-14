package com.rosberry.barcodescanner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rosberry.barcodescanner.scanner.ScannerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, ScannerFragment(), ScannerFragment.TAG)
            commitNow()
        }
    }
}
