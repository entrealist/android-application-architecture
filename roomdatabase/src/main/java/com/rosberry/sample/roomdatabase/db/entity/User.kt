package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap
import com.rosberry.sample.roomdatabase.data.Gender
import org.threeten.bp.LocalDate

/**
 * @author mmikhailov on 12.11.2018.
 */
@Entity(
        tableName = "user",
        indices = [ Index("email", unique = true) ]
)
data class User(
        var email: String,
        var firstName: String? = null,
        var lastName: String? = null,
        var birthday: LocalDate? = null,
        var gender: Gender = Gender.UNKNOWN,
        @Ignore
        var picture: Bitmap? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    // Secondary constructor is needed due to Room cannot match ignored nullable parameter with field.
    constructor(email: String) : this(email, picture = null)
}