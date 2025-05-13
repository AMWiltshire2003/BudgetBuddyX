package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.databinding.ActivityRegisterBinding
import com.yourapp.expensetracker.data.AppDatabase
import com.yourapp.expensetracker.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val userExists = db.userDao().getUserByEmail(email)
                    if (userExists == null) {
                        db.userDao().insertUser(User(username = username, email = email, password = password))
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Email already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
