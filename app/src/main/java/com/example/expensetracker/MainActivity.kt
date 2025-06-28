package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateCategory: Button
    private lateinit var recyclerCategories: RecyclerView
    private val categoryList = mutableListOf<String>()
    private lateinit var adapter: CategoryAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateCategory = findViewById(R.id.btnCreateCategory)
        recyclerCategories = findViewById(R.id.recyclerCategories)

        adapter = CategoryAdapter(categoryList) { category ->
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("category", category)
            startActivity(intent)
        }

        recyclerCategories.layoutManager = LinearLayoutManager(this)
        recyclerCategories.adapter = adapter

        btnCreateCategory.setOnClickListener {
            showCreateCategoryDialog()
        }

        //  Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> true
                R.id.nav_list -> {
                    startActivity(Intent(this, ExpenseListActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
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

    private fun showCreateCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Category")
        val input = android.widget.EditText(this)
        input.hint = "Enter category name"
        builder.setView(input)
        builder.setPositiveButton("Add") { _, _ ->
            val category = input.text.toString()
            if (category.isNotEmpty()) {
                categoryList.add(category)
                adapter.notifyItemInserted(categoryList.size - 1)
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}
