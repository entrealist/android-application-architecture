package com.rosberry.android.sample.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.rosberry.android.sample.R
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
    }

    fun onShowDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 -> }, year, month, day).show()
    }
}
