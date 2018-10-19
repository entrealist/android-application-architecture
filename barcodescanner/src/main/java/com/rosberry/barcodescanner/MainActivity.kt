package com.rosberry.barcodescanner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rosberry.barcodescanner.scanner.ScannerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scan.setOnClickListener { startScanner() }
    }

    private fun startScanner() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, ScannerFragment(), ScannerFragment.TAG)
            commitNow()
        }

        scan.visibility = View.GONE
    }

    fun showResult(text: String) {
        supportFragmentManager.findFragmentByTag(ScannerFragment.TAG)?.let {
            supportFragmentManager.beginTransaction().apply {
                remove(it)
                commitNow()
            }
        }

        result.text = text
        scan.visibility = View.VISIBLE
    }
}
