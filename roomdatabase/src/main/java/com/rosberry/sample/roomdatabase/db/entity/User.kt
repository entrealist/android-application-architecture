package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
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
        @Ignore
        var picture: Bitmap?
) {
    // Secondary constructor is needed due to Room cannot match ignored nullable parameter with field.
    // Setting the parameter null does not affect.
    constructor(
            id: Long,
            email: String,
            firstName: String?,
            lastName: String?,
            birthDay: LocalDate?,
            commentsPosted: Long
    ) : this(id, email, firstName, lastName, birthDay, commentsPosted, null)
}