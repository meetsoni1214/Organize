package com.example.organize.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(emailAccount: EmailAccount)

    @Update
    suspend fun update(emailAccount: EmailAccount)

    @Delete
    suspend fun delete(emailAccount: EmailAccount)

    @Query("SELECT * FROM emailaccount WHERE id = :id")
    fun getItem(id: Int): Flow<EmailAccount>

    @Query("SELECT * FROM emailaccount ORDER BY title ASC")
    fun getItems(): Flow<List<EmailAccount>>

}