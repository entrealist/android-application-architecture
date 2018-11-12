package com.rosberry.sample.roomdatabase.db

/**
 * @author mmikhailov on 12.11.2018.
 */
object DbConstants {

    const val databaseName = "app-database"
    const val schemaVersion = 1

    // primary key id field name
    const val id = "id"

    object User {
        const val tableName = "user"
        const val email = "email"
        const val firstName = "firstName"
        const val lastName = "lastName"
        const val birthday = "birthday"
        const val commentsPosted = "commentsPosted"
    }

    object Article {
        const val tableName = "article"
        const val title = "title"
        const val text = "text"
        const val postedAt = "postedAt"
        const val views = "views"
    }

    object Comment {
        const val tableName = "comment"
        const val text = "text"
        const val postedAt = "postedAt"
        const val userId = "userId"
        const val articleId = "articleId"
    }
}