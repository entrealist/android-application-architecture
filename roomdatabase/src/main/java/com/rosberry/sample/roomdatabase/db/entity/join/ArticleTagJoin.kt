package com.rosberry.sample.roomdatabase.db.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.rosberry.sample.roomdatabase.db.entity.Article
import com.rosberry.sample.roomdatabase.db.entity.Tag

/**
 * @author mmikhailov on 16.11.2018.
 */
@Entity(tableName = "article_tag_join",
        primaryKeys = ["article_id", "tag_id"],
        foreignKeys = [
            ForeignKey(
                    entity = Article::class,
                    parentColumns = ["id"],
                    childColumns = ["article_id"]
            ),
            ForeignKey(entity = Tag::class,
                    parentColumns = ["id"],
                    childColumns = ["tag_id"]
            )
        ]
)
data class ArticleTagJoin(
        @ColumnInfo(name = "article_id")
        var articleId: Long,
        @ColumnInfo(name = "tag_id")
        var tagId: Long
)