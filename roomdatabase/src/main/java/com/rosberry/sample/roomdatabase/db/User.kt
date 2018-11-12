package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.Insert
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.graphics.Bitmap
import org.threeten.bp.LocalDate

/**
 * @author mmikhailov on 12.11.2018.
 */
@Entity(
        tableName = "user",
        indices = [ Index("email", unique = true) ]
)
data class User(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var email: String,
        var firstName: String?,
        var lastName: String?,
        var birthDay: LocalDate?,
        var commentsPosted: Long = 0L,
        @Ignore var picture: Bitmap?
) {
    // secondary constructor is needed due to Room cannot match ignored nullable parameter with field
    // setting the parameter null does not affect
    constructor(
            id: Long,
            email: String,
            firstName: String?,
            lastName: String?,
            birthDay: LocalDate?,
            commentsPosted: Long
    ) : this(id, email, firstName, lastName, birthDay, commentsPosted, null)
}

@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun nukeUsers()
}