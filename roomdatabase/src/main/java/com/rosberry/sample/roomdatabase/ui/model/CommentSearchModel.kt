package com.rosberry.sample.roomdatabase.ui.model

import androidx.room.ColumnInfo
import org.threeten.bp.Instant

/**
 * @author mmikhailov on 13.11.2018.
 */
data class CommentSearchModel(
        @ColumnInfo(name = "id") var commentId: Long,
        @ColumnInfo(name = "title") var articleTitle: String,
        @ColumnInfo(name = "firstName") var userFirstName: String,
        @ColumnInfo(name = "lastName") var userLastName: String,
        @ColumnInfo(name = "email") var userEmail: String,
        @ColumnInfo(name = "postedAt") var commentPostedDate: Instant
)