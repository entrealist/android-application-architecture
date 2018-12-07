package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.rosberry.sample.roomdatabase.data.TagType

/**
 * @author mmikhailov on 16.11.2018.
 */
@Entity(tableName = "tag")
data class Tag(
        var text: String,
        var type: TagType
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}