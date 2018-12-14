package com.rosberry.sample.roomdatabase

import android.arch.core.executor.testing.InstantTaskExecutorRule
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
import java.io.IOException

/**
 * @author mmikhailov on 13.11.2018.
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

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
    fun getAllUsersWhenNoUserInserted() {
        db.userDao()
            .allUsers
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun insertAndGetUserByFullName() {
        // given
        val user = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        // when inserting a new user in the data source
        db.userDao()
            .insert(user)

        // when subscribing to the emissions of the user
        db.userDao()
            .findUserByFullName(user.firstName!!, user.lastName!!)
            .test()
            // then assertValue asserts that there was only one emission of the user
            .assertValue {
                val foundUser = it.first()
                // then the emitted user is the expected one
                foundUser.firstName.equals(user.firstName) &&
                        foundUser.lastName.equals(user.lastName)
            }
    }

    @Test
    fun insertAndDeleteByName() {
        // given
        val user1 = User(
                "u1@e.com",
                "badName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        val user2 = User(
                "u2@e.com",
                "u2FName", "badName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 2 }

        // when
        db.userDao()
            .insertAll(listOf(user1, user2))

        // when
        val numOfDeletedRow = db.userDao()
            .deleteUsersByName("badName")

        // then
        if (numOfDeletedRow != 2) {
            throw AssertionError("There are rows with bad name in DB!")
        }
    }

    @Test
    fun updateAndGetUser() {
        val user = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        val insertedUserId = db.userDao()
            .insert(user)

        val updatedUser = User(user.email, "newFirstName", user.lastName,
                user.birthday)
            .apply { id = insertedUserId }
        val updatedUserId = db.userDao()
            .insert(updatedUser)

        if (insertedUserId != updatedUserId) throw AssertionError("Updated and inserted ids are not equal")

        db.userDao()
            .findUserByFullName("newFirstName", user.lastName!!)
            .test()
            .assertValue {
                it.first()
                    .firstName.equals("newFirstName")
            }
    }

    @Test
    fun deleteUserAndGetNoUser() {
        // given
        val user = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        // when
        db.userDao()
            .insert(user)

        // when
        db.userDao()
            .delete(user)

        // when
        db.userDao()
            .allUsers
            .test()
            // then
            .assertValue { it.isEmpty() }
    }

    @Test
    fun deleteAllUsersAndGetNoUser() {
        // given
        val user = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        // when
        db.userDao()
            .insert(user)

        // when
        db.userDao()
            .nukeUsers()

        // when
        db.userDao()
            .allUsers
            .test()
            // then
            .assertValue { it.isEmpty() }
    }

    @Test
    fun updateExistingUser() {
        // given
        val user = User(
                "u1@e.com",
                "u1FName", "u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

        val updatedUser = User(
                "u1@e.com_new",
                "u1FName_new", "u1LName_new",
                LocalDate.of(2000, 10, 10)
        )
            .apply { id = 1 }

        // when
        db.userDao()
            .insert(user)

        // when
        db.userDao()
            .update(updatedUser)

        // when
        db.userDao()
            .allUsers
            .test()
            // then
            .assertValue {
                it.isNotEmpty() &&
                        it[0].id == updatedUser.id &&
                        it[0].email == updatedUser.email
            }
    }

    @Test
    fun updateNonExistingUser() {
        // given
        val updatedUser = User(
                "u1@e.com_new",
                "u1FName_new", "u1LName_new",
                LocalDate.of(2000, 10, 10)
        )
            .apply { id = 1 }

        // when
        db.userDao()
            .update(updatedUser)

        // when
        db.userDao()
            .allUsers
            .test()
            // then
            .assertValue { it.isEmpty() }
    }

    @Test
    fun insertBulkWithDuplicate_replaceOnConflictStrategy() {
        // given
        val fakeUser1 = User(
                "fake-u1@e.com",
                "fake-u1FName", "fake-u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

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

        // when adding single user
        db.userDao()
            .insert(user1)

        // and when perform bulk adding with duplicated user id
        db.userDao()
            .insertAll(listOf(fakeUser1, user2, user3))

        // and when querying all users
        db.userDao()
            .allUsers
            .test()
            // then
            .assertValue {
                it.size == 3 &&
                        it[0].email == fakeUser1.email
            }
    }

    @Test
    fun insertBulkWithDuplicate_ignoreOnConflictStrategy() {
        // given
        val fakeUser1 = User(
                "fake-u1@e.com",
                "fake-u1FName", "fake-u1LName",
                LocalDate.of(2018, 1, 1)
        )
            .apply { id = 1 }

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

        // when adding single user
        db.userDao()
            .insert(user1)

        // and when perform bulk adding with duplicated user id
        db.userDao()
            .insertAllOrIgnore(listOf(fakeUser1, user2, user3))

        // and when querying all users
        db.userDao()
            .allUsers
            .test()
            // then
            .assertValue {
                it.size == 3 &&
                        it[0].email == user1.email
            }
    }

    @Test
    fun getUsersCommentedArticle() {
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

        val comments = listOf(
                Comment("comment 1 text", Instant.now(), user1.id!!,
                        article1.id!!),
                Comment("comment 2 text", Instant.now(), user2.id!!,
                        article1.id!!),
                Comment("comment 3 text", Instant.now(), user2.id!!,
                        article1.id!!),
                Comment("comment 4 text", Instant.now(), user2.id!!,
                        article1.id!!),
                Comment("comment 5 text", Instant.now(), user3.id!!,
                        article2.id!!)
        )

        // when
        db.userDao()
            .insertAll(listOf(user1, user2, user3))
        db.articleDao()
            .insertAll(listOf(article1, article2, article3))
        db.commentDao()
            .insertAll(comments)

        // when
        db.userDao()
            .findUsersCommentedArticle(article1.id!!)
            .test()
            // then
            .assertValue {
                it.size == 2 &&
                        it[0].id == user1.id &&
                        it[1].id == user2.id
            }

        // when
        db.userDao()
            .findUsersCommentedArticle(article2.id!!)
            .test()
            // then
            .assertValue {
                it.size == 1 &&
                        it[0].id == user3.id
            }

        // when
        db.userDao()
            .findUsersCommentedArticle(article3.id!!)
            .test()
            // then
            .assertValue { it.isEmpty() }
    }
}