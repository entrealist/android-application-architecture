package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(tableName = "article")
data class Article(
        @PrimaryKey(autoGenerate = true) var id: Long,
        var title: String,
        var text: String,
        var postedAt: Instant,
        var views: Long = 0L
)

@Dao
interface ArticleDao {

    @get:Query("SELECT * FROM article ORDER BY article.title ASC")
    val allArticlesOrderedByTitle: Flowable<List<Article>>

    @get:Query("SELECT * FROM article ORDER BY article.postedAt DESC")
    val allArticlesOrderedByDate: Flowable<List<Article>>

    @Query("SELECT * FROM article WHERE article.id = :id")
    fun findArticleById(id: Long): Article

    @Query("SELECT * FROM article WHERE article.title LIKE '%' || :query || '%'")
    fun findArticlesByTitle(query: String): List<Article>

    @Query("SELECT * FROM article WHERE article.postedAt >= :from AND article.postedAt <= :to")
    fun findArticlesWithinDates(from: Instant, to: Instant): List<Article>

    @Query("SELECT * FROM article WHERE article.views > 0")
    fun findArticlesWithViews(): List<Article>

    @Query("SELECT * FROM article INNER JOIN comment ON comment.article_id = article.id")
    fun findArticlesWithComments(): List<Article>

    @Query("SELECT * FROM article INNER JOIN comment ON comment.article_id LIKE article.id WHERE comment.user_id LIKE :userId")
    fun findArticlesWithCommentsFromUserById(userId: Long): List<Article>

    @Query("SELECT * FROM article INNER JOIN comment ON comment.article_id = article.id INNER JOIN user ON comment.user_id = user.id WHERE user.email LIKE :userEmail")
    fun findArticlesWithCommentsFromUserByEmail(userEmail: String): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article): Long

    @Insert
    fun insertAll(vararg articles: Article): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: Article)

    @Delete
    fun delete(article: Article)

    @Query("DELETE FROM article")
    fun nukeArticles()
}