package com.example.organize.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SocialMediaAccount::class], version = 1, exportSchema = false)
abstract class SocialMediaRoomDatabase: RoomDatabase() {

    abstract fun socialMediaDao(): SocialMediaDao

    companion object {

        @Volatile
        private var INSTANCE: SocialMediaRoomDatabase? = null
        fun getDatabase(context: Context): SocialMediaRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SocialMediaRoomDatabase::class.java,
                    "social_media_account_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}