package com.yoonsoobin.insecuremoviereview.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yoonsoobin.insecuremoviereview.dao.ReviewDao
import com.yoonsoobin.insecuremoviereview.dao.UserDao
import com.yoonsoobin.insecuremoviereview.entity.Review
import com.yoonsoobin.insecuremoviereview.entity.User

@Database(entities = [User::class, Review::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        private const val DATABASE_NAME: String = "my_database"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}