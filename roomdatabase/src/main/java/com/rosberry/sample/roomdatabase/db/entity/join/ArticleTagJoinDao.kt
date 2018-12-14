package com.rosberry.sample.roomdatabase.db.entity.join

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.rosberry.sample.roomdatabase.db.entity.Article
import com.rosberry.sample.roomdatabase.db.entity.Tag
import io.reactivex.Single

/**
 * @author mmikhailov on 16.11.2018.
 */
@Dao
interface ArticleTagJoinDao {

    @Query("SELECT * FROM article INNER JOIN article_tag_join ON article.id = article_tag_join.article_id WHERE article_tag_join.tag_id = :tagId")
    fun findArticlesForTag(tagId: Long): Single<List<Article>>

    @Query("SELECT * FROM tag INNER JOIN article_tag_join ON tag.id = article_tag_join.tag_id WHERE article_tag_join.article_id = :articleId")
    fun findTagsForArticle(articleId: Long): Single<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleTagJoin: ArticleTagJoin): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllOrIgnore(joins: List<ArticleTagJoin>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(joins: List<ArticleTagJoin>): List<Long>

    @Update
    fun update(join: ArticleTagJoin)

    @Delete
    fun delete(join: ArticleTagJoin): Int

    @Query("DELETE FROM article_tag_join")
    fun nukeItems(): Int
}