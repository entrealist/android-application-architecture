package com.rosberry.sample.roomdatabase.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.rosberry.sample.roomdatabase.db.entity.User
import io.reactivex.Flowable

/**
 * @author mmikhailov on 07/12/2018.
 */
@Dao
interface UserDao {

    @get:Query("SELECT * FROM user")
    val allUsers: Flowable<List<User>>

    @Query("SELECT * FROM user WHERE firstName = :firstName AND lastName = :lastName")
    fun findUserByFullName(firstName: String, lastName: String): List<User>

    @Query("SELECT user.* FROM user INNER JOIN comment ON comment.user_id = user.id INNER JOIN article ON article.id = comment.article_id WHERE article.id LIKE :articleId")
    fun findUsersCommentedArticle(articleId: Long): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrReplace(vararg users: User): List<Long>

    @Insert
    fun insertAll(vararg users: User): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateComment(user: User)

    @Delete
    fun delete(user: User): Int

    @Query("DELETE FROM user WHERE firstName LIKE :badName OR lastName LIKE :badName")
    fun deleteUsersByName(badName: String): Int

    @Query("DELETE FROM user")
    fun nukeUsers(): Int
}