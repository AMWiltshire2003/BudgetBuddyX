package com.example.expensetracker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ExpenseViewModel.kt
class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: ExpenseRepository

    init {
        val db = ExpenseDatabase.getDatabase(application)
        repo = ExpenseRepository(db.expenseDao())
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repo.insert(expense)
    }

    fun getExpenses(category: String): LiveData<List<Expense>> {
        return repo.getExpensesByCategory(category)
    }
}
