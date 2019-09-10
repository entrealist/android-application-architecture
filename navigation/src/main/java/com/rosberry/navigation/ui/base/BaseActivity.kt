package com.rosberry.navigation.ui.base

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity() {

    protected abstract val layoutRes: Int

    protected abstract fun handleBackPress(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onBackPressed() {
        var fragment: androidx.fragment.app.Fragment? = null
        for (f in supportFragmentManager.fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }

        if (fragment != null && fragment is BaseFragment && fragment.onBackPressed()) {
            return
        } else if (!handleBackPress()) {
            super.onBackPressed()
        }
    }
}
