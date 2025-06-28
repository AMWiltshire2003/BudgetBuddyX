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

    @Query("SELECT COUNT(*) FROM expenses WHERE startTime >= strftime('%s', 'now', 'start of day')")
    suspend fun getExpensesCountToday(): Int

    // Add these two for graph and visualization features:
    @Query("SELECT * FROM expenses WHERE startTime >= :since")
    suspend fun getLast30DaysExpenses(since: Long): List<Expense>

    @Query("SELECT * FROM expenses WHERE startTime BETWEEN :start AND :end")
    suspend fun getExpensesBetween(start: Long, end: Long): List<Expense>

}
