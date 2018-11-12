package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import android.content.Context
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author mmikhailov on 11.11.2018.
 */
@Database(
        entities = [
            Article::class,
            Comment::class,
            User::class
        ],
        version = DbConstants.schemaVersion
)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun commentDao(): CommentDao
    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    DbConstants.databaseName)
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}

internal class DbConverters {

    private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun toLocalDate(value: String?) = value?.let { localDateFormatter.parse(it, LocalDate::from) }

    @TypeConverter
    fun toString(date: LocalDate?) = date?.format(localDateFormatter)

    @TypeConverter
    fun toInstant(millis: Long?) = millis?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun toLongMillis(instant: Instant?) = instant?.toEpochMilli()
}