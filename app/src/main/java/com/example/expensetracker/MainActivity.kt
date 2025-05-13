package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
