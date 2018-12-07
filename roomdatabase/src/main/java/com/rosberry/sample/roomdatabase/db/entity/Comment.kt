package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.rosberry.sample.roomdatabase.db.DbConstants
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(
        tableName = DbConstants.Comment.tableName,
        foreignKeys = [
            ForeignKey(
                    entity = User::class,
                    parentColumns = [DbConstants.id],
                    childColumns = [DbConstants.Comment.userId]
            ),
            ForeignKey(
                    entity = Article::class,
                    parentColumns = [DbConstants.id],
                    childColumns = [DbConstants.Comment.articleId]
            )
        ],
        indices = [
            Index(DbConstants.Comment.userId),
            Index(DbConstants.Comment.articleId)
        ]
)
data class Comment(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbConstants.id)
        var id: Long,
        @ColumnInfo(name = DbConstants.Comment.text)
        var text: String,
        @ColumnInfo(name = DbConstants.Comment.postedAt)
        var postedAt: Instant,
        @ColumnInfo(name = DbConstants.Comment.userId)
        var userId: Long,
        @ColumnInfo(name = DbConstants.Comment.articleId)
        var articleId: Long
)