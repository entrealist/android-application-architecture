package com.rosberry.sample.roomdatabase.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.rosberry.sample.roomdatabase.db.entity.Article
import io.reactivex.Flowable
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 07/12/2018.
 */
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

    @Query("SELECT article.* FROM article INNER JOIN comment ON comment.article_id = article.id")
    fun findArticlesWithComments(): List<Article>

    @Query("SELECT article.* FROM article INNER JOIN comment ON comment.article_id LIKE article.id WHERE comment.user_id LIKE :userId")
    fun findArticlesWithCommentsFromUserById(userId: Long): List<Article>

    @Query("SELECT article.* FROM article INNER JOIN comment ON comment.article_id = article.id INNER JOIN user ON comment.user_id = user.id WHERE user.email LIKE :userEmail")
    fun findArticlesWithCommentsFromUserByEmail(userEmail: String): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article): Long

    @Insert // todo проверить на дефолтной стратегии что будет если, например, 1 из 20 записей будет повторяться.
    fun insertAll(vararg articles: Article): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: Article)

    @Delete
    fun delete(article: Article): Int

    @Query("DELETE FROM article")
    fun nukeArticles(): Int
}