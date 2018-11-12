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
        @ColumnInfo(name = DbConstants.Comment.text) var text: String,
        @ColumnInfo(name = DbConstants.Comment.postedAt) var postedAt: Instant,
        @ColumnInfo(name = DbConstants.Comment.userId) var userId: Long,
        @ColumnInfo(name = DbConstants.Comment.articleId) var articleId: Long
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