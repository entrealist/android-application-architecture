package com.rosberry.sample.roomdatabase.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rosberry.sample.roomdatabase.db.entity.User

/**
 * @author mmikhailov on 07/12/2018.
 */
@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun nukeUsers()
}