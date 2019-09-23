package com.rosberry.sample.roomdatabase.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(tableName = "article")
data class Article(
        var title: String,
        var text: String,
        var postedAt: Instant,
        var views: Long = 0L
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}