package com.example.organize

import android.app.Application
import com.example.organize.data.EmailRoomDatabase
import com.example.organize.data.SocialMediaRoomDatabase

class EmailApplication : Application() {
    //This will create the database (the physical database on the disk) on the first access.
    val database: EmailRoomDatabase by lazy { EmailRoomDatabase.getDatabase(this) }

    val socialMediaDatabase: SocialMediaRoomDatabase by lazy { SocialMediaRoomDatabase.getDatabase(this)}
}