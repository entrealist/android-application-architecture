package com.rosberry.sample.roomdatabase.db.utils

import android.os.AsyncTask
import com.rosberry.sample.roomdatabase.db.AppDatabase
import com.rosberry.sample.roomdatabase.db.Article
import com.rosberry.sample.roomdatabase.db.Comment
import com.rosberry.sample.roomdatabase.db.User
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

object DatabaseInitializer {

    fun populateAsync(db: AppDatabase) {
        val task = PopulateDbAsync(db)
        task.execute()
    }

    fun populateSync(db: AppDatabase) {
        populateWithTestData(db)
    }

    private fun addComment(db: AppDatabase, commentText: String,
                           user: User, article: Article, timestamp: Instant) {

        val comment = Comment(commentText, timestamp, user.id!!, article.id!!)
        db.commentDao()
            .insert(comment)
    }

    private fun addArticle(db: AppDatabase, title: String,
                           text: String, timestamp: Instant, views: Long): Article {

        val article = Article(title, text, timestamp, views)
        db.articleDao()
            .insert(article)

        return article
    }

    private fun addUser(db: AppDatabase, email: String, firstName: String,
                        lastName: String, birthday: LocalDate): User {

        val user = User(email, firstName, lastName, birthday)
        db.userDao()
            .insert(user)

        return user
    }

    private fun populateWithTestData(db: AppDatabase) {
        db.articleDao()
            .nukeArticles()
        db.commentDao()
            .nukeComments()
        db.userDao()
            .nukeUsers()

        // users
        val user1 = addUser(
                db, "jason1968@email.com",
                "Jason", "Statham",
                LocalDate.of(1968, 12, 21)
        )
        val user2 = addUser(
                db, "Leon1977@email.com",
                "Leon", "Kennedy",
                LocalDate.of(1977, 5, 27)
        )
        val user3 = addUser(
                db, "claire1994@email.com",
                "Claire", "Redfield",
                LocalDate.of(1994, 3, 8)
        )
        val user4 = addUser(
                db, "chris1990@email.com",
                "Chris", "Redfield",
                LocalDate.of(1990, 2, 23)
        )

        // articles
        val articleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        val todayDateTimestamp = getTodayDateMinusDays(0)
        val yesterdayDateTimestamp = getTodayDateMinusDays(1)
        val twoDaysAgoDateTimestamp = getTodayDateMinusDays(2)
        val lastWeekDateTimestamp = getTodayDateMinusDays(7)
        val twoWeeksAgoDateTimestamp = getTodayDateMinusDays(14)

        val article1 = addArticle(
                db, "Article 1", articleText,
                twoWeeksAgoDateTimestamp,
                1544)
        val article2 = addArticle(
                db, "Article 2", articleText,
                lastWeekDateTimestamp,
                768)
        val article3 = addArticle(
                db, "Article 3", articleText,
                twoDaysAgoDateTimestamp,
                364)
        val article4 = addArticle(
                db, "Article 4", articleText,
                todayDateTimestamp,
                3)
        val article5 = addArticle(
                db, "Article 5", articleText,
                yesterdayDateTimestamp,
                257)

        // comments
        val timestamp1 = getDateTimeOf(2018, 7, 31, 3, 33)
        val timestamp2 = getDateTimeOf(2018, 2, 1, 1, 7)
        val timestamp3 = getDateTimeOf(2018, 5, 27, 18, 59)
        val timestamp4 = getDateTimeOf(2018, 7, 28, 9, 41)
        val timestamp5 = getDateTimeOf(2018, 2, 5, 8, 36)
        val timestamp6 = getDateTimeOf(2018, 3, 11, 0, 15)
        val timestamp7 = getDateTimeOf(2018, 4, 19, 10, 12)
        val timestamp8 = getDateTimeOf(2018, 6, 18, 13, 38)
        val timestamp9 = getDateTimeOf(2018, 10, 23, 23, 45)
        val timestamp10 = getDateTimeOf(2018, 11, 13, 4, 20)

        addComment(db, "comment 1 text",
                user1, article1,
                timestamp1
        )
        addComment(db, "comment 2 text",
                user2, article1,
                timestamp2
        )
        addComment(db, "comment 3 text",
                user2, article1,
                timestamp3
        )
        addComment(db, "comment 4 text",
                user3, article2,
                timestamp4
        )
        addComment(db, "comment 5 text",
                user3, article2,
                timestamp5
        )
        addComment(db, "comment 6 text",
                user3, article3,
                timestamp6
        )
        addComment(db, "comment 7 text",
                user4, article4,
                timestamp7
        )
        addComment(db, "comment 8 text",
                user4, article4,
                timestamp8
        )
        addComment(db, "comment 9 text",
                user4, article5,
                timestamp9
        )
        addComment(db, "comment 10 text",
                user4, article5,
                timestamp10
        )
    }

    private fun getTodayDateMinusDays(daysAmount: Long): Instant {
        return LocalDate.now()
            .apply { minusDays(daysAmount) }
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC)
    }

    private fun getDateTimeOf(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int): Instant {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute)
            .toInstant(ZoneOffset.UTC)
    }

    private class PopulateDbAsync(private val mDb: AppDatabase) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            populateWithTestData(mDb)
            return null
        }
    }
}
