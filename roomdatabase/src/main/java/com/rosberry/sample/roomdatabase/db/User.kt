package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.graphics.Bitmap
import io.reactivex.Flowable
import org.threeten.bp.LocalDate

/**
 * @author mmikhailov on 12.11.2018.
 */
@Entity(
        tableName = "user",
        indices = [ Index("email", unique = true) ]
)
data class User(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var email: String,
        var firstName: String?,
        var lastName: String?,
        var birthDay: LocalDate?,
        var commentsPosted: Long = 0L,
        @Ignore var picture: Bitmap?
) {
    // Secondary constructor is needed due to Room cannot match ignored nullable parameter with field.
    // Setting the parameter null does not affect.
    constructor(
            id: Long,
            email: String,
            firstName: String?,
            lastName: String?,
            birthDay: LocalDate?,
            commentsPosted: Long
    ) : this(id, email, firstName, lastName, birthDay, commentsPosted, null)
}

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

    @Insert(onConflict = IGNORE)
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