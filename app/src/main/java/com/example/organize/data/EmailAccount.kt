package com.example.organize.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emailaccount")
data class EmailAccount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val accountTitle: String,
    @ColumnInfo(name = "email") val accountEmail: String,
    @ColumnInfo(name = "password") val accountPassword: String,
    @ColumnInfo(name = "remarks") val accountRemarks: String
)
