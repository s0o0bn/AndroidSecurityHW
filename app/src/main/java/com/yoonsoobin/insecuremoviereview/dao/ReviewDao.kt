package com.yoonsoobin.insecuremoviereview.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoonsoobin.insecuremoviereview.vo.ReviewItem
import com.yoonsoobin.insecuremoviereview.entity.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(review: Review)

    @Query(
        "SELECT title, score FROM users JOIN reviews ON users.username = reviews.username " +
                "WHERE users.username = :username"
    )
    fun getAll(username: String): Flow<List<ReviewItem>>
}