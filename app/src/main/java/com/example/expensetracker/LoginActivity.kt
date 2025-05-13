package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.yourapp.expensetracker.data.AppDatabase
import com.yourapp.expensetracker.data.UserDao
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "budget_buddy_db"
        ).allowMainThreadQueries().build()

        userDao = db.userDao()

        val loginButton: Button = findViewById(R.id.buttonLogin)
        val googleButton: Button = findViewById(R.id.buttonGoogleSignIn)
        val registerText: TextView = findViewById(R.id.textViewRegister)

        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()

            lifecycleScope.launch {
                val user = userDao.login(username, password)
                if (user != null) {
                    // Login successful, navigate to main/dashboard
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish() // Optional: prevent going back to login on back press
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


            registerText.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }

            googleButton.setOnClickListener {
                // Implement Google sign-in separately
            }
        }
    }