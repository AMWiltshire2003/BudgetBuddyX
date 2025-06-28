package com.example.expensetracker

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.util.AppSettings

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val newContext = AppSettings.applyLocale(newBase)
        super.attachBaseContext(newContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
