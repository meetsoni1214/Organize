package com.example.organize.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BankAccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bankAccount: BankAccount)

    @Update
    suspend fun update(bankAccount: BankAccount)

    @Delete
    suspend fun delete(bankAccount: BankAccount)

    @Query("SELECT * FROM bank_account WHERE id = :id")
    fun getItem(id: Int): Flow<BankAccount>

    @Query("SELECT * FROM bank_account ORDER BY bank_name ASC")
    fun getItems(): Flow<List<BankAccount>>
}