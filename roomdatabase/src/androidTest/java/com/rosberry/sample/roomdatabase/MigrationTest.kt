package com.rosberry.sample.roomdatabase

import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.testing.MigrationTestHelper
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rosberry.sample.roomdatabase.data.Gender
import com.rosberry.sample.roomdatabase.db.AppDatabase
import com.rosberry.sample.roomdatabase.db.Migrations.MIGRATION_1_2
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException



/**
 * @author mmikhailov on 14.11.2018.
 */
@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val testDbName = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            AppDatabase::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        helper.createDatabase(testDbName, 1)
            .apply {
                val values = ContentValues().apply {
                    put("id", 1)
                    put("email", "a@a.a")
                    put("firstName", "firstName")
                    put("lastName", "lastName")
                    put("birthDay", "2011-12-03")
                }

                insert("user", SQLiteDatabase.CONFLICT_REPLACE, values)
                close()
            }

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        val db = helper.runMigrationsAndValidate(testDbName, 2, true, MIGRATION_1_2)
        val cursor = db.query("SELECT * FROM user")

        assertEquals(1, cursor.count)

        while (cursor.moveToNext()) {
            val genderString = cursor.getString(cursor.getColumnIndex("gender"))
            assertEquals(Gender.UNKNOWN.name, genderString)
        }
    }
}