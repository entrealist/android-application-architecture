package com.rosberry.sample.workmanager.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/**
 * @author mmikhailov on 16.04.2019
 */
@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder WHERE id = :id")
    fun findReminderById(id: Long): ReminderEntity?

    @Insert(onConflict = REPLACE)
    fun insert(entity: ReminderEntity): Long
}