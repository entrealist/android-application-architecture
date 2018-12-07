package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.rosberry.sample.roomdatabase.db.DbConstants
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(tableName = DbConstants.Article.tableName)
data class Article(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbConstants.id)
        var id: Long,
        @ColumnInfo(name = DbConstants.Article.title)
        var title: String,
        @ColumnInfo(name = DbConstants.Article.text)
        var text: String,
        @ColumnInfo(name = DbConstants.Article.postedAt)
        var postedAt: Instant,
        @ColumnInfo(name = DbConstants.Article.views)
        var views: Long = 0L
)