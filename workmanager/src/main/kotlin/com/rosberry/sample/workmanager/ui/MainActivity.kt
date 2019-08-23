package com.rosberry.sample.workmanager.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rosberry.sample.workmanager.R
import com.rosberry.sample.workmanager.di.Injector
import com.rosberry.sample.workmanager.domain.Frequency
import com.rosberry.sample.workmanager.domain.Frequency.EVERYDAY
import com.rosberry.sample.workmanager.domain.Frequency.EVERY_FIFTH_DAY
import com.rosberry.sample.workmanager.domain.Frequency.EVERY_FOURTH_DAY
import com.rosberry.sample.workmanager.domain.Frequency.EVERY_SECOND_DAY
import com.rosberry.sample.workmanager.domain.Frequency.EVERY_SEVENTH_DAY
import com.rosberry.sample.workmanager.domain.Frequency.EVERY_SIXTH_DAY
import com.rosberry.sample.workmanager.domain.Frequency.EVERY_THIRD_DAY
import com.rosberry.sample.workmanager.presentation.MainPresenter
import com.rosberry.sample.workmanager.presentation.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate(this)

        frequencyGroup.setOnCheckedChangeListener { _, checkedId ->
            val frequency = when (checkedId) {
                R.id.everyday -> EVERYDAY
                R.id.every2 -> EVERY_SECOND_DAY
                R.id.every3 -> EVERY_THIRD_DAY
                R.id.every4 -> EVERY_FOURTH_DAY
                R.id.every5 -> EVERY_FIFTH_DAY
                R.id.every6 -> EVERY_SIXTH_DAY
                R.id.every7 -> EVERY_SEVENTH_DAY
                else -> throw IllegalStateException("Something illegal occurred")
            }

            presenter.check(frequency)
        }

        startDate.setOnClickListener { presenter.clickStartDate() }
        endDate.setOnClickListener { presenter.clickEndDate() }
        addTime.setOnClickListener { presenter.clickAddTime() }
        clearTime.setOnClickListener { presenter.clickClear() }
        saveBtn.setOnClickListener { presenter.clickSave() }
    }

    override fun showDatePickerDialog(date: LocalDate, minDate: Long, dateSelectedListener: (Int, Int, Int) -> Unit) {
        DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    dateSelectedListener.invoke(year, month, dayOfMonth)
                },
                date.year,
                date.monthValue - 1,
                date.dayOfMonth
        ).apply {
            datePicker.minDate = minDate
        }
            .show()
    }

    override fun showTimePickerDialog(time: LocalTime, timeSelectedListener: (Int, Int) -> Unit) {
        TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    timeSelectedListener.invoke(hourOfDay, minute)
                },
                time.hour,
                time.minute,
                true
        ).show()
    }

    override fun setData(
            startDate: String,
            endDate: String,
            frequency: Frequency,
            timetable: List<String>
    ) {
        this.startDate.setText(startDate)
        this.endDate.setText(endDate)

        val radio = when (frequency) {
            EVERYDAY -> everyday
            EVERY_SECOND_DAY -> every2
            EVERY_THIRD_DAY -> every3
            EVERY_FOURTH_DAY -> every4
            EVERY_FIFTH_DAY -> every5
            EVERY_SIXTH_DAY -> every6
            EVERY_SEVENTH_DAY -> every7
        }

        radio.isChecked = true

        for (entry in timetable) {
            this.timetable.append(entry)
            this.timetable.append("\n")
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun setStartDate(text: String) {
        startDate.setText(text)
    }

    override fun setEndDate(text: String) {
        endDate.setText(text)
    }

    override fun setTimetable(timetable: List<String>) {
        this.timetable.text = ""

        for (entry in timetable) {
            this.timetable.append(entry)
            this.timetable.append("\n")
        }
    }
}
