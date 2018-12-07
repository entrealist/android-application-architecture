package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(tableName = "article")
data class Article(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var title: String,
        var text: String,
        var postedAt: Instant,
        var views: Long = 0L
)