package com.rosberry.sample.roomdatabase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rosberry.sample.roomdatabase.db.AppDatabase
import com.rosberry.sample.roomdatabase.db.entity.Article
import com.rosberry.sample.roomdatabase.db.entity.Comment
import com.rosberry.sample.roomdatabase.db.entity.User
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import java.io.IOException

/**
 * @author mmikhailov on 14.11.2018.
 */
@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

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
    fun getAllArticlesOrderedByTitle() {
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

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))

        // when
        db.articleDao()
            .allArticlesOrderedByTitle
            .test()
            // then
            .assertValue {
                it.size == 4 &&
                        it[0].title == article3.title &&
                        it[1].title == article2.title &&
                        it[2].title == article1.title &&
                        it[3].title == article4.title
            }
    }

    @Test
    fun getAllArticlesOrderedByDate() {
        // given
        val baseTimestamp = Instant.now()
        val dayAgoTimestamp = baseTimestamp.minus(1, ChronoUnit.DAYS)
        val secondAgoTimestamp = baseTimestamp.minus(1, ChronoUnit.SECONDS)
        val secondAfterTimestamp = baseTimestamp.plus(1, ChronoUnit.SECONDS)

        val article1 = Article(
                "article 1 title", "article 1 text",
                secondAgoTimestamp,
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "article 2 title", "article 2 text",
                baseTimestamp,
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "article 3 title", "article 3 text",
                secondAfterTimestamp,
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "article 4 title", "article 4 text",
                dayAgoTimestamp,
                81
        )
            .apply { id = 4 }

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))

        // when
        db.articleDao()
            .allArticlesOrderedByDate
            .test()
            // then
            .assertValue {
                it.size == 4 &&
                        it[0].postedAt == article3.postedAt &&
                        it[1].postedAt == article2.postedAt &&
                        it[2].postedAt == article1.postedAt &&
                        it[3].postedAt == article4.postedAt
            }
    }

    @Test
    fun getArticleById() {
        // given
        val article = Article(
                "title", "article text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        // when
        db.articleDao()
            .insert(article)

        // when
        db.articleDao()
            .findArticleById(article.id!!)
            .test()
            // then
            .assertValue { it.id == article.id }
    }

    @Test
    fun getErrorByQueryingNotExistingArticle() {
        // given
        val article = Article(
                "title", "article text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        // when
        db.articleDao()
            .insert(article)

        // when
        db.articleDao()
            .findArticleById(500)
            .test()
            // then
            .assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun searchArticlesByTitle() {
        // given
        val article1 = Article(
                "article 1 title", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "search text", "article 2 text",
                Instant.now(),
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "text search android", "article 3 text",
                Instant.now(),
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "title of article 4", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))

        // when
        db.articleDao()
            .findArticlesByTitle("search")
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == article2.id &&
                        it[1].id == article3.id
            }

        // when
        db.articleDao()
            .findArticlesByTitle("artic")
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == article1.id &&
                        it[1].id == article4.id
            }

        // when
        db.articleDao()
            .findArticlesByTitle("ar")
            .test()
            // then
            .assertValue {
                it.size == 4 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id &&
                        it[2].id == article3.id &&
                        it[3].id == article4.id
            }

        // when
        db.articleDao()
            .findArticlesByTitle("ios")
            .test()
            // then
            .assertValue { it.isEmpty() }
            .assertComplete()
    }

    @Test
    fun getArticlesWithinDates() {
        // given
        val baseTimestamp = Instant.now()
        val yearAgoTimestamp = baseTimestamp.minus(365, ChronoUnit.DAYS)
        val monthAgoTimestamp = baseTimestamp.minus(30, ChronoUnit.DAYS)
        val dayAgoTimestamp = baseTimestamp.minus(1, ChronoUnit.DAYS)
        val secondAgoTimestamp = baseTimestamp.minus(1, ChronoUnit.SECONDS)
        val secondAfterTimestamp = baseTimestamp.plus(1, ChronoUnit.SECONDS)

        val article1 = Article(
                "article 1 title", "article 1 text",
                secondAgoTimestamp,
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "article 2 title", "article 2 text",
                baseTimestamp,
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "article 3 title", "article 3 text",
                secondAfterTimestamp,
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "article 4 title", "article 4 text",
                dayAgoTimestamp,
                81
        )
            .apply { id = 4 }

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))

        // when
        db.articleDao()
            .findArticlesWithinDates(secondAgoTimestamp, baseTimestamp)
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id
            }

        // when
        db.articleDao()
            .findArticlesWithinDates(yearAgoTimestamp, monthAgoTimestamp)
            .test()
            // then
            .assertValue { it.isEmpty() }
            .assertComplete()
    }

    @Test
    fun getArticlesWithViews() {
        // given
        val article1 = Article(
                "article 1 title", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "article 2 title", "article 2 text",
                Instant.now(),
                0
        )
            .apply { id = 2 }

        val article3 = Article(
                "article 3 title", "article 3 text",
                Instant.now(),
                0
        )
            .apply { id = 3 }

        val article4 = Article(
                "article 4 title", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        // when
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))

        // when
        db.articleDao()
            .findArticlesWithViews()
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == article1.id &&
                        it[1].id == article4.id
            }
    }

    @Test
    fun getArticlesWithComments() {
        // given
        val user1 = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "u2LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 2 }

        val user3 = User(
                "u3@e.com",
                "u3FName", "u3LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 3 }

        val article1 = Article(
                "Article 1", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "Article 2", "article 2 text",
                Instant.now(),
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "Article 3", "article 3 text",
                Instant.now(),
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "Article 4", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        val comments = listOf(
                Comment("comment 1 text", Instant.now(), user1.id!!,
                        article1.id!!).apply { id = 1 },
                Comment("comment 2 text", Instant.now(), user2.id!!,
                        article2.id!!).apply { id = 2 },
                Comment("comment 3 text", Instant.now(), user2.id!!,
                        article1.id!!).apply { id = 3 },
                Comment("comment 4 text", Instant.now(), user3.id!!,
                        article2.id!!).apply { id = 4 },
                Comment("comment 5 text", Instant.now(), user2.id!!,
                        article1.id!!).apply { id = 5 }
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))
        db.commentDao()
            .insertAll(comments)

        // when
        db.articleDao()
            .findArticlesWithComments()
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id
            }
    }

    @Test
    fun getArticlesWithCommentsByUserId() {
        // given
        val user1 = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "u2LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 2 }

        val user3 = User(
                "u3@e.com",
                "u3FName", "u3LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 3 }

        val article1 = Article(
                "Article 1", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "Article 2", "article 2 text",
                Instant.now(),
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "Article 3", "article 3 text",
                Instant.now(),
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "Article 4", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        val comments = listOf(
                Comment("comment 1 text", Instant.now(), user1.id!!,
                        article1.id!!).apply { id = 1 },
                Comment("comment 2 text", Instant.now(), user2.id!!,
                        article2.id!!).apply { id = 2 },
                Comment("comment 3 text", Instant.now(), user2.id!!,
                        article1.id!!).apply { id = 3 },
                Comment("comment 4 text", Instant.now(), user3.id!!,
                        article4.id!!).apply { id = 4 },
                Comment("comment 5 text", Instant.now(), user2.id!!,
                        article3.id!!).apply { id = 5 },
                Comment("comment 6 text", Instant.now(), user2.id!!,
                        article3.id!!).apply { id = 6 }
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))
        db.commentDao()
            .insertAll(comments)

        // when
        db.articleDao()
            .findArticlesWithCommentsByUserId(user2.id!!)
            .test()
            // then
            .assertValue {
                it.size == 3 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id &&
                        it[2].id == article3.id
            }
    }

    @Test
    fun getArticlesWithCommentsByUserEmail() {
        // given
        val user1 = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "u2LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 2 }

        val user3 = User(
                "u3@e.com",
                "u3FName", "u3LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 3 }

        val article1 = Article(
                "Article 1", "article 1 text",
                Instant.now(),
                78
        )
            .apply { id = 1 }

        val article2 = Article(
                "Article 2", "article 2 text",
                Instant.now(),
                79
        )
            .apply { id = 2 }

        val article3 = Article(
                "Article 3", "article 3 text",
                Instant.now(),
                80
        )
            .apply { id = 3 }

        val article4 = Article(
                "Article 4", "article 4 text",
                Instant.now(),
                81
        )
            .apply { id = 4 }

        val comments = listOf(
                Comment("comment 1 text", Instant.now(), user1.id!!,
                        article1.id!!).apply { id = 1 },
                Comment("comment 2 text", Instant.now(), user2.id!!,
                        article2.id!!).apply { id = 2 },
                Comment("comment 3 text", Instant.now(), user2.id!!,
                        article1.id!!).apply { id = 3 },
                Comment("comment 4 text", Instant.now(), user3.id!!,
                        article4.id!!).apply { id = 4 },
                Comment("comment 5 text", Instant.now(), user2.id!!,
                        article3.id!!).apply { id = 5 },
                Comment("comment 6 text", Instant.now(), user2.id!!,
                        article3.id!!).apply { id = 6 }
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3, article4))
        db.commentDao()
            .insertAll(comments)

        // when
        db.articleDao()
            .findArticlesWithCommentsByUserEmail(user2.email)
            .test()
            // then
            .assertValue {
                it.size == 3 &&
                        it[0].id == article1.id &&
                        it[1].id == article2.id &&
                        it[2].id == article3.id
            }
    }
}