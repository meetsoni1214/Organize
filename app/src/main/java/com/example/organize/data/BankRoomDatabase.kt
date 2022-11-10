package com.example.organize.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BankAccount::class], version = 2, exportSchema = false)
abstract class BankRoomDatabase: RoomDatabase() {

    abstract fun bankAccountDao(): BankAccountDao

    companion object {

        @Volatile
        private var INSTANCE: BankRoomDatabase? = null
        fun getDatabase(context: Context): BankRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BankRoomDatabase::class.java,
                    "bank_accounts_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}