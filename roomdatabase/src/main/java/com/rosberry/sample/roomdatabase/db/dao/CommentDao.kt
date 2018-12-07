package com.rosberry.sample.roomdatabase.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rosberry.sample.roomdatabase.db.entity.Comment

/**
 * @author mmikhailov on 07/12/2018.
 */
@Dao
interface CommentDao {

    @Insert
    fun insertAll(vararg comments: Comment)

    @Delete
    fun delete(comment: Comment)

    @Query("DELETE FROM comment")
    fun nukeComments()
}