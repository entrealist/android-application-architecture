package com.rosberry.sample.roomdatabase.db.entity

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.rosberry.sample.roomdatabase.ui.model.CommentSearchModel
import io.reactivex.Flowable
import io.reactivex.Single
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Dao
interface CommentDao {

    @get:Query("SELECT * FROM comment")
    val allComments: Flowable<List<Comment>>

    @get:Query("SELECT comment.id, article.title, user.firstName, user.lastName, user.email, comment.postedAt FROM comment INNER JOIN article ON comment.article_id = article.id INNER JOIN user ON comment.user_id = user.id ")
    val allCommentsAsSearchModel: Flowable<List<CommentSearchModel>>

    @Query("SELECT comment.id, article.title AS title, user.firstName AS firstName, user.lastName AS lastName, user.email AS email, comment.postedAt FROM article INNER JOIN comment ON comment.article_id = article.id INNER JOIN user ON user.id = comment.user_id WHERE user.email LIKE :userEmail AND comment.postedAt > :after")
    fun findCommentsAsSearchModelByUserNameAfterDate(userEmail: String, after: Instant): Single<List<CommentSearchModel>>

    @Query("SELECT comment.* FROM comment INNER JOIN user ON user.id = comment.user_id WHERE user.id = :userId")
    fun findCommentsOfUser(userId: Long): Single<List<Comment>>

    @Insert(onConflict = REPLACE)
    fun insert(comment: Comment): Long

    @Insert(onConflict = IGNORE)
    fun insertAllOrIgnore(users: List<Comment>): List<Long>

    @Insert(onConflict = REPLACE)
    fun insertAll(comments: List<Comment>): List<Long>

    @Update
    fun update(comment: Comment)

    @Delete
    fun delete(comment: Comment): Int

    @Query("DELETE FROM comment")
    fun nukeComments(): Int
}