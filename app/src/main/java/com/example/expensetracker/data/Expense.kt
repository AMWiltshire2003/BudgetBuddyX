package com.example.expensetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

 //Expense.kt
    @Entity(tableName = "expenses")
    data class Expense(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val category: String,
        val amount: Double,
        val description: String,
        val startTime: Long,
        val endTime: Long,
        val photoUri: String?
    )

