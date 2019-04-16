package com.rosberry.sample.workmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rosberry.sample.workmanager.domain.Frequency
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author mmikhailov on 14.01.2019
 */
@Database(
        entities = [ReminderEntity::class],
        version = 1
)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao
}

internal class DbConverters {

    private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val localTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun stringToLocalDate(value: String?) = value?.let { localDateFormatter.parse(it, LocalDate::from) }

    @TypeConverter
    fun localDateToString(date: LocalDate?) = date?.format(localDateFormatter)

    @TypeConverter
    fun stringToTimetable(jsonString: String?): List<LocalTime>? {
        if (jsonString == null) {
            return null
        }

        val type = object : TypeToken<List<String>>() {}.type
        val timetableStringList: List<String> = Gson().fromJson(jsonString, type)

        return timetableStringList.asSequence()
            .map { localTimeFormatter.parse(it, LocalTime::from) }
            .toList()
    }

    @TypeConverter
    fun timetableToString(timetable: List<LocalTime>?): String? {
        if (timetable == null) {
            return null
        }

        val timetableStringList = timetable.asSequence()
            .map { it.format(localTimeFormatter) }
            .toList()

        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(timetableStringList, type)
    }

    @TypeConverter
    fun frequencyEnumToString(enumValue: Frequency) = enumValue.name

    @TypeConverter
    fun stringToFrequencyEnum(string: String) = Frequency.valueOf(string)
}