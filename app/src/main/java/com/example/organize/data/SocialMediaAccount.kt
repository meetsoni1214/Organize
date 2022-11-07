package com.example.organize.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "social_media_accounts")
data class SocialMediaAccount (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title")val accountTitle: String,
    @ColumnInfo(name = "username")val accountLogin: String,
    @ColumnInfo(name = "password")val accountPassword: String,
    @ColumnInfo(name = "remarks")val accountRemarks: String,
    @ColumnInfo(name = "icon")val accountIcon: Int = 0
        )
