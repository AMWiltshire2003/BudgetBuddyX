package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class SettingsActivity : BaseActivity() {

    private lateinit var spinnerCurrency: Spinner
    private lateinit var spinnerLanguage: Spinner
    private lateinit var switchNotifications: Switch
    private lateinit var prefs: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs.edit().putFloat("min_goal", 50f).apply()
        prefs.edit().putFloat("max_goal", 500f).apply()

        spinnerCurrency = findViewById(R.id.spinnerCurrency)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        switchNotifications = findViewById(R.id.switchNotifications)
        prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Restore saved settings
        spinnerCurrency.setSelection(prefs.getInt("currency_index", 0))
        spinnerLanguage.setSelection(prefs.getInt("language_index", 0))
        switchNotifications.isChecked = prefs.getBoolean("notifications_enabled", true)

        // Save settings on change
        val currencies = resources.getStringArray(R.array.currency_array)
        val languages = resources.getStringArray(R.array.language_array)

        spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                prefs.edit()
                    .putInt("currency_index", position)
                    .putString("currency_symbol", currencies[position])
                    .apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                prefs.edit()
                    .putInt("language_index", position)
                    .putString("language_code", when (languages[position]) {
                        "English" -> "en"
                        "Afrikaans" -> "af"
                        "Zulu" -> "zu"
                        "French" -> "fr"
                        "Spanish" -> "sp"
                        else -> "en"
                    })
                    .apply()

                //  Restart activity to apply language
                recreate()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        AlertDialog.Builder(this)
            .setMessage("Language changed. Restart app to apply changes?")
            .setPositiveButton("Restart") { _, _ ->
                finishAffinity()
                startActivity(Intent(this, SplashActivity::class.java))
            }
            .show()


        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply()
        }

        findViewById<Button>(R.id.btnChangeUsername).setOnClickListener {
            Toast.makeText(this, "Change Username Clicked", Toast.LENGTH_SHORT).show()
            // Add logic to show dialog or navigate
        }

        findViewById<Button>(R.id.btnChangePassword).setOnClickListener {
            Toast.makeText(this, "Change Password Clicked", Toast.LENGTH_SHORT).show()
            // Add logic to show dialog or navigate
        }

        //  Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_settings

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
                R.id.nav_gamification -> {
                    startActivity(Intent(this, BudgetKeeperActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_settings -> true
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase?.getSharedPreferences("settings", MODE_PRIVATE)
        val langCode = prefs?.getString("language_code", "en") ?: "en"
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        val newContext = newBase?.createConfigurationContext(config)
        if (newContext != null) {
            super.attachBaseContext(newContext)
        }
    }

}
