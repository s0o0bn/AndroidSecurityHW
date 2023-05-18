package com.yoonsoobin.insecuremoviereview.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoonsoobin.insecuremoviereview.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(user: User)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun findByUsername(username: String): User
}