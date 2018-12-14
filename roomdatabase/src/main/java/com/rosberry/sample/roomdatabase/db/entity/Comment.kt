package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(
        tableName = "comment",
        foreignKeys = [
            ForeignKey(
                    entity = User::class,
                    parentColumns = ["id"],
                    childColumns = ["user_id"]
            ),
            ForeignKey(
                    entity = Article::class,
                    parentColumns = ["id"],
                    childColumns = ["article_id"]
            )
        ],
        indices = [
            Index("user_id"),
            Index("article_id")
        ]
)
data class Comment(
        var text: String,
        var postedAt: Instant,
        @ColumnInfo(name = "user_id")
        var userId: Long,
        @ColumnInfo(name = "article_id")
        var articleId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}