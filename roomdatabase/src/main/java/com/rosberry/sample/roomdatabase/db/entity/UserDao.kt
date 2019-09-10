package com.rosberry.sample.roomdatabase.db.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author mmikhailov on 12.11.2018.
 */
@Dao
interface UserDao {

    @get:Query("SELECT * FROM user")
    val allUsers: Flowable<List<User>>

    @Query("SELECT * FROM user WHERE firstName = :firstName AND lastName = :lastName")
    fun findUserByFullName(firstName: String, lastName: String): Single<List<User>>

    @Query("SELECT DISTINCT user.* FROM user INNER JOIN comment ON comment.user_id = user.id INNER JOIN article ON article.id = comment.article_id WHERE article.id LIKE :articleId")
    fun findUsersCommentedArticle(articleId: Long): Single<List<User>>

    @Insert(onConflict = REPLACE)
    fun insert(user: User): Long

    @Insert(onConflict = IGNORE)
    fun insertAllOrIgnore(users: List<User>): List<Long>

    @Insert(onConflict = REPLACE)
    fun insertAll(users: List<User>): List<Long>

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User): Int

    @Query("DELETE FROM user WHERE firstName LIKE :badName OR lastName LIKE :badName")
    fun deleteUsersByName(badName: String): Int

    @Query("DELETE FROM user")
    fun nukeUsers(): Int
}