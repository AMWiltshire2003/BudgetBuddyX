package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class GamificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification)

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

    fun openBudgetKeeper(view: android.view.View) {
        // Launch a BudgetKeeperActivity or show a dialog (depending on how you design it)
        val intent = Intent(this, BudgetKeeperActivity::class.java)
        startActivity(intent)
    }
}
