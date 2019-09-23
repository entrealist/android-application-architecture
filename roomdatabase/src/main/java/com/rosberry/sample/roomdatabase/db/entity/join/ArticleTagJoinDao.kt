package com.rosberry.sample.roomdatabase.db.entity.join

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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