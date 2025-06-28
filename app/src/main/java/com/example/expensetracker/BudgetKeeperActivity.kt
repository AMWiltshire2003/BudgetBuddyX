package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class BudgetKeeperActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_keeper)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewBadges)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BadgeAdapter()
        recyclerView.adapter = adapter

        val db = AppDatabase.getInstance(this)

        lifecycleScope.launch {
            val badges = db.badgeDao().getAllBadges()
            adapter.submitList(badges)
        }

        //  Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_gamification

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
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
                R.id.nav_gamification -> true
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
