package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.rosberry.sample.roomdatabase.ui.model.CommentSearchModel
import io.reactivex.Flowable
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
        @ColumnInfo(name = "user_id") var userId: Long,
        @ColumnInfo(name = "article_id") var articleId: Long
) {
    @PrimaryKey(autoGenerate = true) var id: Long? = null
}

@Dao
interface CommentDao {

    @get:Query("SELECT * FROM comment")
    val allComments: Flowable<List<Comment>>

    @get:Query("SELECT comment.id, article.title, user.firstName, user.lastName, user.email, comment.postedAt FROM comment INNER JOIN article ON comment.article_id = article.id INNER JOIN user ON comment.user_id = user.id ")
    val allCommentsAsSearchModel: Flowable<List<CommentSearchModel>>

    @Query("SELECT comment.id, article.title AS title, user.firstName AS firstName, user.lastName AS lastName, user.email AS email, comment.postedAt FROM article INNER JOIN comment ON comment.article_id = article.id INNER JOIN user ON user.id = comment.user_id WHERE user.email LIKE :userEmail AND comment.postedAt > :after")
    fun findCommentsAsSearchModelByUserNameAfterDate(userEmail: String, after: Instant): List<CommentSearchModel>

    @Query("SELECT comment.* FROM comment INNER JOIN user ON user.id = comment.user_id WHERE user.id = :userId")
    fun findCommentsOfUser(userId: Long): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment): Long

    @Insert
    fun insertAll(vararg comments: Comment): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateComment(comment: Comment)

    @Delete
    fun delete(comment: Comment): Int

    @Query("DELETE FROM comment")
    fun nukeComments(): Int
}