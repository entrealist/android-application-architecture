package com.rosberry.sample.roomdatabase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rosberry.sample.roomdatabase.db.AppDatabase
import com.rosberry.sample.roomdatabase.db.Article
import com.rosberry.sample.roomdatabase.db.Comment
import com.rosberry.sample.roomdatabase.db.User
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
class CommentDaoTest {

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
    fun getAllCommentsAsSearchModel() {
        // given
        val user1 = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "u2LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 2 }

        val user3 = User(
                "u3@e.com",
                "u3FName", "u3LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 3 }

        val article1 = Article(
                "Article 1", "article 1 text",
                Instant.now(),
                78
        ).apply { id = 1 }

        val article2 = Article(
                "Article 2", "article 2 text",
                Instant.now(),
                79
        ).apply { id = 2 }

        val article3 = Article(
                "Article 3", "article 3 text",
                Instant.now(),
                80
        ).apply { id = 3 }

        val comments = listOf(
                Comment("comment 1 text", Instant.now(), user1.id!!, article1.id!!).apply { id = 1 },
                Comment("comment 2 text", Instant.now(), user2.id!!, article1.id!!).apply { id = 2 },
                Comment("comment 3 text", Instant.now(), user2.id!!, article1.id!!).apply { id = 3 },
                Comment("comment 4 text", Instant.now(), user2.id!!, article1.id!!).apply { id = 4 },
                Comment("comment 5 text", Instant.now(), user3.id!!, article2.id!!).apply { id = 5 }
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3))
        db.commentDao()
            .insertAll(comments)

        // when
        db.commentDao()
            .allCommentsAsSearchModel
            .test()
            // then
            .assertValue {
                it.size == 5 &&
                        it[0].commentId == comments[0].id &&
                        it[0].articleTitle == article1.title &&
                        it[0].userFirstName == user1.firstName &&
                        it[0].userLastName == user1.lastName &&
                        it[0].userEmail == user1.email &&
                        it[0].commentPostedDate == comments[0].postedAt &&

                        it[1].commentId == comments[1].id &&
                        it[1].articleTitle == article1.title &&
                        it[1].userFirstName == user2.firstName &&
                        it[1].userLastName == user2.lastName &&
                        it[1].userEmail == user2.email &&
                        it[1].commentPostedDate == comments[1].postedAt &&

                        it[2].commentId == comments[2].id &&
                        it[2].articleTitle == article1.title &&
                        it[2].userFirstName == user2.firstName &&
                        it[2].userLastName == user2.lastName &&
                        it[2].userEmail == user2.email &&
                        it[2].commentPostedDate == comments[2].postedAt &&

                        it[3].commentId == comments[3].id &&
                        it[3].articleTitle == article1.title &&
                        it[3].userFirstName == user2.firstName &&
                        it[3].userLastName == user2.lastName &&
                        it[3].userEmail == user2.email &&
                        it[3].commentPostedDate == comments[3].postedAt &&

                        it[4].commentId == comments[4].id &&
                        it[4].articleTitle == article2.title &&
                        it[4].userFirstName == user3.firstName &&
                        it[4].userLastName == user3.lastName &&
                        it[4].userEmail == user3.email &&
                        it[4].commentPostedDate == comments[4].postedAt
            }
    }

    @Test
    fun getCommentsAsSearchModelByUserNameAfterDate() {
        // given
        val baseTimestamp = Instant.now()
        val dayAgoTimestamp = baseTimestamp.minus(1, ChronoUnit.DAYS)
        val secondAgoTimestamp = baseTimestamp.minus(1, ChronoUnit.SECONDS)
        val secondAfterTimestamp = baseTimestamp.plus(1, ChronoUnit.SECONDS)
        val dayAfterTimestamp = baseTimestamp.plus(1, ChronoUnit.DAYS)

        val user1 = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "u2LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 2 }

        val user3 = User(
                "u3@e.com",
                "u3FName", "u3LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 3 }

        val article1 = Article(
                "Article 1", "article 1 text",
                Instant.now(),
                78
        ).apply { id = 1 }

        val article2 = Article(
                "Article 2", "article 2 text",
                Instant.now(),
                79
        ).apply { id = 2 }

        val article3 = Article(
                "Article 3", "article 3 text",
                Instant.now(),
                80
        ).apply { id = 3 }

        val comments = listOf(
                Comment("comment 1 text", dayAgoTimestamp, user1.id!!, article1.id!!).apply { id = 1 },
                Comment("comment 2 text", secondAgoTimestamp, user2.id!!, article1.id!!).apply { id = 2 },
                Comment("comment 3 text", baseTimestamp, user2.id!!, article1.id!!).apply { id = 3 },
                Comment("comment 4 text", secondAfterTimestamp, user2.id!!, article1.id!!).apply { id = 4 },
                Comment("comment 5 text", dayAfterTimestamp, user2.id!!, article2.id!!).apply { id = 5 },
                Comment("comment 6 text", dayAfterTimestamp, user3.id!!, article2.id!!).apply { id = 6 }
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3))
        db.commentDao()
            .insertAll(comments)

        // when
        db.commentDao()
            .findCommentsAsSearchModelByUserNameAfterDate(user2.email, baseTimestamp)
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].commentId == comments[3].id &&
                        it[0].articleTitle == article1.title &&
                        it[0].userFirstName == user2.firstName &&
                        it[0].userLastName == user2.lastName &&
                        it[0].userEmail == user2.email &&
                        it[0].commentPostedDate == comments[3].postedAt &&

                        it[1].commentId == comments[4].id &&
                        it[1].articleTitle == article2.title &&
                        it[1].userFirstName == user2.firstName &&
                        it[1].userLastName == user2.lastName &&
                        it[1].userEmail == user2.email &&
                        it[1].commentPostedDate == comments[4].postedAt
            }
    }

    @Test
    fun getCommentsOfUser() {
        // given
        val user1 = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "u2LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 2 }

        val user3 = User(
                "u3@e.com",
                "u3FName", "u3LName",
                LocalDate.of(2018, 1, 1)
        ).apply { id = 3 }

        val article1 = Article(
                "Article 1", "article 1 text",
                Instant.now(),
                78
        ).apply { id = 1 }

        val article2 = Article(
                "Article 2", "article 2 text",
                Instant.now(),
                79
        ).apply { id = 2 }

        val article3 = Article(
                "Article 3", "article 3 text",
                Instant.now(),
                80
        ).apply { id = 3 }

        val comments = listOf(
                Comment("comment 1 text", Instant.now(), user1.id!!, article1.id!!).apply { id = 1 },
                Comment("comment 2 text", Instant.now(), user2.id!!, article1.id!!).apply { id = 2 },
                Comment("comment 3 text", Instant.now(), user2.id!!, article1.id!!).apply { id = 3 },
                Comment("comment 4 text", Instant.now(), user2.id!!, article1.id!!).apply { id = 4 },
                Comment("comment 5 text", Instant.now(), user3.id!!, article2.id!!).apply { id = 5 }
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3))
        db.commentDao()
            .insertAll(comments)

        // when
        db.commentDao()
            .findCommentsOfUser(user2.id!!)
            .test()
            // then
            .assertValue {
                it.size == 3 &&
                        it[0].id == comments[1].id &&
                        it[0].text == comments[1].text &&
                        it[0].postedAt == comments[1].postedAt &&
                        it[0].userId == comments[1].userId &&
                        it[0].articleId == comments[1].articleId &&

                        it[1].id == comments[2].id &&
                        it[1].text == comments[2].text &&
                        it[1].postedAt == comments[2].postedAt &&
                        it[1].userId == comments[2].userId &&
                        it[1].articleId == comments[2].articleId &&

                        it[2].id == comments[3].id &&
                        it[2].text == comments[3].text &&
                        it[2].postedAt == comments[3].postedAt &&
                        it[2].userId == comments[3].userId &&
                        it[2].articleId == comments[3].articleId
            }
    }
}