package com.example.expensetracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// ExpenseDao.kt
@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses WHERE category = :category")
    fun getExpensesByCategory(category: String): LiveData<List<Expense>>
}
