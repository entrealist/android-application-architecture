package com.rosberry.android.sample.ui.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.rosberry.android.sample.R
import com.rosberry.android.sample.ui.base.model.ActivityModel

abstract class BaseActivity : AppCompatActivity() {

    var stateSaved = false
    val model = ActivityModel()

    open fun onSoftBackClicked() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(model.layoutId)

        stateSaved = false
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateSaved = true
    }
}
