package com.rosberry.sample.roomdatabase.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rosberry.sample.roomdatabase.R
import com.rosberry.sample.roomdatabase.db.AppDatabase


class MainActivity : AppCompatActivity() {

    private val db: AppDatabase by lazy { AppDatabase.getDatabase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
