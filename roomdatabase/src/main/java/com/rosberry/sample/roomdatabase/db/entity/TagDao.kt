package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
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