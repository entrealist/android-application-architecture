package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap
import com.rosberry.sample.roomdatabase.db.DbConstants
import org.threeten.bp.LocalDate

/**
 * @author mmikhailov on 12.11.2018.
 */
@Entity(
        tableName = DbConstants.User.tableName,
        indices = [ Index(DbConstants.User.email, unique = true) ]
)
data class User(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbConstants.id)
        var id: Long,
        @ColumnInfo(name = DbConstants.User.email)
        var email: String,
        @ColumnInfo(name = DbConstants.User.firstName)
        var firstName: String?,
        @ColumnInfo(name = DbConstants.User.lastName)
        var lastName: String?,
        @ColumnInfo(name = DbConstants.User.birthday)
        var birthDay: LocalDate?,
        @ColumnInfo(name = DbConstants.User.commentsPosted)
        var commentsPosted: Long = 0L,
        @Ignore
        var picture: Bitmap?
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