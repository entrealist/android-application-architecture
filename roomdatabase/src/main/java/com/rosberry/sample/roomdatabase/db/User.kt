package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.graphics.Bitmap
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.threeten.bp.LocalDate

/**
 * @author mmikhailov on 12.11.2018.
 */
@Entity(
        tableName = "user",
        indices = [ Index("email", unique = true) ]
)
data class User(
        var email: String,
        var firstName: String?,
        var lastName: String?,
        var birthDay: LocalDate?,
        @Ignore var picture: Bitmap?
) {
    @PrimaryKey(autoGenerate = true) var id: Long? = null

    // Secondary constructor is needed due to Room cannot match ignored nullable parameter with field.
    // Setting the parameter null does not affect.
    constructor(
            email: String,
            firstName: String?,
            lastName: String?,
            birthDay: LocalDate?
    ) : this(email, firstName, lastName, birthDay, null)
}

@Dao
interface UserDao {

    @get:Query("SELECT * FROM user")
    val allUsers: Flowable<List<User>>

    @Query("SELECT * FROM user WHERE firstName = :firstName AND lastName = :lastName")
    fun findUserByFullName(firstName: String, lastName: String): Maybe<List<User>>

    @Query("SELECT DISTINCT user.* FROM user INNER JOIN comment ON comment.user_id = user.id INNER JOIN article ON article.id = comment.article_id WHERE article.id LIKE :articleId")
    fun findUsersCommentedArticle(articleId: Long): Maybe<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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