package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.ExpenseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExpenseListActivity : AppCompatActivity() {
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter

    @SuppressLint("MissingInflatedId")
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

//  Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_list

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_list -> true
                R.id.nav_graph -> {
                    startActivity(Intent(this, BudgetGraphActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_gamification -> {
                    startActivity(Intent(this, BudgetKeeperActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }


}
