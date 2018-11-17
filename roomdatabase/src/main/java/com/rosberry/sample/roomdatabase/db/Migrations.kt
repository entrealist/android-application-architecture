package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

/**
 * @author mmikhailov on 14.11.2018.
 */
object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.apply {
                execSQL("ALTER TABLE user ADD COLUMN gender TEXT DEFAULT 'UNKNOWN' NOT NULL")
            }
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.apply {
                // todo
            }
        }
    }
}