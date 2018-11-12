package com.rosberry.sample.roomdatabase.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 11.11.2018.
 */
@Entity(tableName = DbConstants.Article.tableName)
data class Article(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbConstants.id)
        var id: Long,
        @ColumnInfo(name = DbConstants.Article.title) var title: String,
        @ColumnInfo(name = DbConstants.Article.text) var text: String,
        @ColumnInfo(name = DbConstants.Article.postedAt) var postedAt: Instant,
        @ColumnInfo(name = DbConstants.Article.views) var views: Long = 0L
)

@Dao
interface ArticleDao {

    @Insert // todo проверить на дефолтной стратегии что будет если, например, 1 из 20 записей будет повторяться.
    fun insertAll(vararg articles: Article)

    @Delete
    fun delete(article: Article)

    @Query("DELETE FROM article")
    fun nukeArticles()
}