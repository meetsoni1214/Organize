package com.example.organize.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SocialMediaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(socialMediaAccount: SocialMediaAccount)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(socialMediaAccounts: List<SocialMediaAccount>)

    @Update
    suspend fun update(socialMediaAccount: SocialMediaAccount)

    @Delete
    suspend fun delete(socialMediaAccount: SocialMediaAccount)

    @Query("SELECT * FROM social_media_accounts WHERE id = :id")
    fun getItem(id: Int): Flow<SocialMediaAccount>

    @Query("SELECT * FROM social_media_accounts ORDER BY title ASC")
    fun getItems(): Flow<List<SocialMediaAccount>>
}