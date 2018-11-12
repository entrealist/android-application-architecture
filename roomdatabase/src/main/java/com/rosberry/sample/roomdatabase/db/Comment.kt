package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.Insert
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
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
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var text: String,
        var postedAt: Instant,
        @ColumnInfo(name = "user_id") var userId: Long,
        @ColumnInfo(name = "article_id") var articleId: Long
)

@Dao
interface CommentDao {

    @Insert
    fun insertAll(vararg comments: Comment)

    @Delete
    fun delete(comment: Comment)

    @Query("DELETE FROM comment")
    fun nukeComments()
}