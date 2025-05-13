package com.example.expensetracker.data

// ExpenseRepository.kt
class ExpenseRepository(private val dao: ExpenseDao) {
    suspend fun insert(expense: Expense) = dao.insert(expense)
    fun getExpensesByCategory(category: String) = dao.getExpensesByCategory(category)
}
