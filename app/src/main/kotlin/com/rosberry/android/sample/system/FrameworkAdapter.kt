package com.rosberry.android.sample.system

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.rosberry.android.sample.data.persistence.internal.ViewDataRepository
import com.rosberry.android.sample.ui.main.MainActivity
import javax.inject.Inject

class FrameworkAdapter @Inject constructor(val viewData: ViewDataRepository) {

    companion object {
        const val ARGS = "args"
    }

    interface OnActivityResult {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    private var intent: Intent? = null
    private var bundle: Bundle? = null

    private val activityResultCallbacks: HashSet<OnActivityResult> = HashSet()
    lateinit var fragmentManager: FragmentManager

    fun saveInstanceState(bundle: Bundle?) {
        viewData.saveInstanceState(bundle)
    }

    fun restoreInstanceState(activity: MainActivity, savedInstanceState: Bundle?) {
        viewData.restoreInstanceState(savedInstanceState)
    }

    fun addActivityResultListener(listener: OnActivityResult) {
        activityResultCallbacks.add(listener)
    }

    fun removeActivityResultListener(listener: OnActivityResult) {
        activityResultCallbacks.remove(listener)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (item in activityResultCallbacks) {
            item.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun attachFragmentManager(supportFragmentManager: FragmentManager) {
        fragmentManager = supportFragmentManager;
    }

    fun setIntentAndBundle(intent: Intent?, bundle: Bundle?) {
        this.intent = intent
        this.bundle = bundle

    }

    fun getIntent() = intent

    fun getBundle() = bundle
}