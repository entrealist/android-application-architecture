package com.rosberry.android.sample.ui.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.rosberry.android.sample.ui.base.model.ActivityModel

abstract class BaseActivity : MvpAppCompatActivity() {

    var stateSaved = false
    val model = ActivityModel()


    open fun onSoftBackClicked() {
        finish()
    }

    override fun onBackPressed() {

        val fragmentList = supportFragmentManager.fragments

        var handled = false
        for (f in fragmentList) {
            if (f is BaseFragment) {
                handled = f.onBackPressed()
                if (handled) break
            }
        }

        if (!handled) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(model.layoutId)
        stateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        stateSaved = true
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        stateSaved = false
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onSoftBackClicked(); true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        when (model.menuId) {
            -1 -> return super.onCreateOptionsMenu(menu)
            else -> menuInflater.inflate(model.menuId, menu)
        }

        return true
    }


}
