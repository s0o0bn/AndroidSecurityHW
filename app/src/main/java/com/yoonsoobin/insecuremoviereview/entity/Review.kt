package com.yoonsoobin.insecuremoviereview.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class Review(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    val id: Long,

    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "score") val score: Float?,
    @ColumnInfo(name = "content") val content: String?,
)
