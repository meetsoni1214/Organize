package com.example.organize.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmailAccount::class], version = 1, exportSchema = false)
abstract class EmailRoomDatabase: RoomDatabase() {

    abstract fun emailDao(): EmailDao

    companion object {

        @Volatile
        private var INSTANCE: EmailRoomDatabase? = null
        fun getDatabase(context: Context): EmailRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmailRoomDatabase::class.java,
                    "emailaccount_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    INSTANCE = instance
                return instance
            }
        }
    }
}