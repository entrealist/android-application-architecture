package com.rosberry.sample.roomdatabase.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rosberry.sample.roomdatabase.db.entity.Article

/**
 * @author mmikhailov on 07/12/2018.
 */
@Dao
interface ArticleDao {

    @Insert // todo проверить на дефолтной стратегии что будет если, например, 1 из 20 записей будет повторяться.
    fun insertAll(vararg articles: Article)

    @Delete
    fun delete(article: Article)

    @Query("DELETE FROM article")
    fun nukeArticles()
}