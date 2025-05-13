package com.yourapp.expensetracker

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var spinnerCurrency: Spinner
    private lateinit var spinnerLanguage: Spinner
    private lateinit var switchNotifications: Switch
    private lateinit var prefs: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        spinnerCurrency = findViewById(R.id.spinnerCurrency)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        switchNotifications = findViewById(R.id.switchNotifications)
        prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Restore saved settings
        spinnerCurrency.setSelection(prefs.getInt("currency_index", 0))
        spinnerLanguage.setSelection(prefs.getInt("language_index", 0))
        switchNotifications.isChecked = prefs.getBoolean("notifications_enabled", true)

        // Save settings on change
        spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                prefs.edit().putInt("currency_index", position).apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                prefs.edit().putInt("language_index", position).apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

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
    }
}
