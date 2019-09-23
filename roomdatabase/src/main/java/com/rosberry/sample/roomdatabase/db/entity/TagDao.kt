package com.rosberry.sample.roomdatabase.db.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Flowable

/**
 * @author mmikhailov on 16.11.2018.
 */
@Dao
interface TagDao {

    @get:Query("SELECT * FROM tag")
    val allTags: Flowable<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllOrIgnore(tags: List<Tag>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tags: List<Tag>): List<Long>

    @Update
    fun update(tag: Tag)

    @Delete
    fun delete(tag: Tag): Int

    @Query("DELETE FROM tag")
    fun nukeTags(): Int
}