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
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Dao
interface ArticleDao {

    @get:Query("SELECT * FROM article ORDER BY article.title ASC")
    val allArticlesOrderedByTitle: Flowable<List<Article>>

    @get:Query("SELECT * FROM article ORDER BY article.postedAt DESC")
    val allArticlesOrderedByDate: Flowable<List<Article>>

    @Query("SELECT * FROM article WHERE article.id = :id")
    fun findArticleById(id: Long): Single<Article>

    @Query("SELECT * FROM article WHERE article.title LIKE '%' || :query || '%'")
    fun findArticlesByTitle(query: String): Single<List<Article>>

    @Query("SELECT * FROM article WHERE article.postedAt >= :from AND article.postedAt <= :to ORDER BY article.title ASC")
    fun findArticlesWithinDates(from: Instant, to: Instant): Single<List<Article>>

    @Query("SELECT * FROM article WHERE article.views > 0 ORDER BY article.title ASC")
    fun findArticlesWithViews(): Single<List<Article>>

    @Query("SELECT DISTINCT article.* FROM article INNER JOIN comment ON comment.article_id = article.id ORDER BY article.title ASC")
    fun findArticlesWithComments(): Single<List<Article>>

    @Query("SELECT DISTINCT article.* FROM article INNER JOIN comment ON comment.article_id LIKE article.id WHERE comment.user_id LIKE :userId ORDER BY article.title ASC")
    fun findArticlesWithCommentsByUserId(userId: Long): Single<List<Article>>

    @Query("SELECT DISTINCT article.* FROM article INNER JOIN comment ON comment.article_id = article.id INNER JOIN user ON comment.user_id = user.id WHERE user.email LIKE :userEmail ORDER BY article.title ASC")
    fun findArticlesWithCommentsByUserEmail(userEmail: String): Single<List<Article>>

    @Insert(onConflict = REPLACE)
    fun insert(article: Article): Long

    @Insert(onConflict = IGNORE)
    fun insertAllOrIgnore(articles: List<Article>): List<Long>

    @Insert(onConflict = REPLACE)
    fun insertAll(articles: List<Article>): List<Long>

    @Update
    fun update(article: Article)

    @Delete
    fun delete(article: Article): Int

    @Query("DELETE FROM article")
    fun nukeArticles(): Int
}