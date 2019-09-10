/*
 * Copyright (c) 2019 Rosberry. All rights reserved.
 */

package com.rosberry.android.preferencemanager.ui

import android.os.Bundle
import com.rosberry.android.preferencemanager.R
import com.rosberry.android.preferencemanager.data.SettingsManager
import com.rosberry.android.preferencemanager.data.UserManager
import com.rosberry.android.preferencemanager.presentation.PreferencePresenter
import com.rosberry.android.preferencemanager.presentation.PreferenceView
import kotlinx.android.synthetic.main.activity_preference.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class PreferenceActivity : MvpAppCompatActivity(), PreferenceView {

    @InjectPresenter
    lateinit var presenter: PreferencePresenter

    @ProvidePresenter
    fun providePresenter(): PreferencePresenter {
        val settingsManager = SettingsManager(applicationContext)
        val userManager = UserManager(applicationContext)
        return PreferencePresenter(settingsManager, userManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        saveButton.setOnClickListener {
            presenter.clickSaveButton(
                    nameView.text.toString(),
                    surnameView.text.toString(),
                    loadOnStartView.isChecked
            )
        }
    }

    override fun name(name: String) {
        nameView.setText(name)
    }

    override fun surname(surname: String) {
        surnameView.setText(surname)
    }
}
