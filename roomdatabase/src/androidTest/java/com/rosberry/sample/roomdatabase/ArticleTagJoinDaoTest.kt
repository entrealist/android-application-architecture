package com.rosberry.sample.roomdatabase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rosberry.sample.roomdatabase.data.TagType
import com.rosberry.sample.roomdatabase.db.AppDatabase
import com.rosberry.sample.roomdatabase.db.entity.Article
import com.rosberry.sample.roomdatabase.db.entity.Tag
import com.rosberry.sample.roomdatabase.db.entity.join.ArticleTagJoin
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Instant
import java.io.IOException

/**
 * @author mmikhailov on 17.11.2018.
 */
@RunWith(AndroidJUnit4::class)
class ArticleTagJoinDaoTest {

    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getTagsForArticle() {
        // given
        val article1 = Article(
                "c", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "b", "article 2 text",
                Instant.now(),
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "a", "article 3 text",
                Instant.now(),
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "d", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        val tag1 = Tag("tag1", TagType.FIRST_TYPE).apply { id = 1 }
        val tag2 = Tag("tag2", TagType.THIRD_TYPE).apply { id = 2 }
        val tag3 = Tag("tag3", TagType.SECOND_TYPE).apply { id = 3 }
        val tag4 = Tag("tag4", TagType.THIRD_TYPE).apply { id = 4 }
        val tag5 = Tag("tag5", TagType.FIRST_TYPE).apply { id = 5 }

        val articleTagJoin1 = ArticleTagJoin(1, 1)
        val articleTagJoin2 = ArticleTagJoin(1, 3)
        val articleTagJoin3 = ArticleTagJoin(1, 4)
        val articleTagJoin4 = ArticleTagJoin(1, 2)
        val articleTagJoin5 = ArticleTagJoin(2, 1)
        val articleTagJoin6 = ArticleTagJoin(2, 4)
        val articleTagJoin7 = ArticleTagJoin(3, 5)

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))
        db.tagDao()
            .insertAll(listOf(tag1, tag2, tag3, tag4, tag5))
        db.articleTagJoinDao()
            .insertAll(listOf(
                    articleTagJoin1,
                    articleTagJoin2,
                    articleTagJoin3,
                    articleTagJoin4,
                    articleTagJoin5,
                    articleTagJoin6,
                    articleTagJoin7
            ))

        // and when
        db.articleTagJoinDao()
            .findTagsForArticle(article1.id!!)
            .test()
            // then
            .assertValue {
                it.size == 4 &&
                        it[0].id == tag1.id &&
                        it[1].id == tag2.id &&
                        it[2].id == tag3.id &&
                        it[3].id == tag4.id
            }

        // and when
        db.articleTagJoinDao()
            .findTagsForArticle(article2.id!!)
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == tag1.id &&
                        it[1].id == tag4.id
            }

        // and when
        db.articleTagJoinDao()
            .findTagsForArticle(article3.id!!)
            .test()
            // then
            .assertValue {
                it.size == 1 &&
                        it[0].id == tag5.id
            }

        // and when
        db.articleTagJoinDao()
            .findTagsForArticle(article4.id!!)
            .test()
            // then
            .assertValue { it.isEmpty() }
    }

    @Test
    fun getArticlesForTags() {
        // given
        val article1 = Article(
                "c", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "b", "article 2 text",
                Instant.now(),
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "a", "article 3 text",
                Instant.now(),
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "d", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        val tag1 = Tag("tag1", TagType.FIRST_TYPE).apply { id = 1 }
        val tag2 = Tag("tag2", TagType.THIRD_TYPE).apply { id = 2 }
        val tag3 = Tag("tag3", TagType.SECOND_TYPE).apply { id = 3 }
        val tag4 = Tag("tag4", TagType.THIRD_TYPE).apply { id = 4 }
        val tag5 = Tag("tag5", TagType.FIRST_TYPE).apply { id = 5 }

        val articleTagJoin1 = ArticleTagJoin(1, 1)
        val articleTagJoin2 = ArticleTagJoin(1, 3)
        val articleTagJoin3 = ArticleTagJoin(1, 4)
        val articleTagJoin4 = ArticleTagJoin(1, 2)
        val articleTagJoin5 = ArticleTagJoin(2, 1)
        val articleTagJoin6 = ArticleTagJoin(2, 4)
        val articleTagJoin7 = ArticleTagJoin(3, 4)

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))
        db.tagDao()
            .insertAll(listOf(tag1, tag2, tag3, tag4, tag5))
        db.articleTagJoinDao()
            .insertAll(listOf(
                    articleTagJoin1,
                    articleTagJoin2,
                    articleTagJoin3,
                    articleTagJoin4,
                    articleTagJoin5,
                    articleTagJoin6,
                    articleTagJoin7
            ))

        // and when
        db.articleTagJoinDao()
            .findArticlesForTag(tag1.id!!)
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id
            }

        // and when
        db.articleTagJoinDao()
            .findArticlesForTag(tag2.id!!)
            .test()
            // then
            .assertValue {
                it.size == 1 &&
                        it[0].id == article1.id
            }

        // and when
        db.articleTagJoinDao()
            .findArticlesForTag(tag3.id!!)
            .test()
            // then
            .assertValue {
                it.size == 1 &&
                        it[0].id == article1.id
            }

        // and when
        db.articleTagJoinDao()
            .findArticlesForTag(tag4.id!!)
            .test()
            // then
            .assertValue {
                it.size == 3 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id &&
                        it[2].id == article3.id
            }

        // and when
        db.articleTagJoinDao()
            .findArticlesForTag(tag5.id!!)
            .test()
            // then
            .assertValue { it.isEmpty() }
    }
}