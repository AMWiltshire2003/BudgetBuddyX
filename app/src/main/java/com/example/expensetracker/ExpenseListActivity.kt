package com.example.expensetracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.ExpenseViewModel

class ExpenseListActivity : AppCompatActivity() {
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerExpenses)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val category = intent.getStringExtra("category") ?: "Unknown"

        tvTitle.text = "Expenses for $category"
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        viewModel.getExpenses(category).observe(this) { expenseList ->
            adapter = ExpenseAdapter(expenseList)
            recyclerView.adapter = adapter
        }
    }
}
