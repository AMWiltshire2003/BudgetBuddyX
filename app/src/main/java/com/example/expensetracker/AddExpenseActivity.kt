package com.example.expensetracker

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.Expense
import com.example.expensetracker.data.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var tvCategory: TextView
    private lateinit var etAmount: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnStartTime: Button
    private lateinit var btnEndTime: Button
    private lateinit var ivPhoto: ImageView
    private lateinit var btnAddPhoto: Button
    private lateinit var btnSaveExpense: Button

    private var startTime: Long = 0
    private var endTime: Long = 0
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        tvCategory = findViewById(R.id.tvCategory)
        etAmount = findViewById(R.id.etAmount)
        etDescription = findViewById(R.id.etDescription)
        btnStartTime = findViewById(R.id.btnStartTime)
        btnEndTime = findViewById(R.id.btnEndTime)
        ivPhoto = findViewById(R.id.ivPhoto)
        btnAddPhoto = findViewById(R.id.btnAddPhoto)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)

        val category = intent.getStringExtra("category")
        tvCategory.text = "Category: $category"

        btnStartTime.setOnClickListener {
            showDateTimePicker { time ->
                startTime = time
                btnStartTime.text = "Start: ${dateFormat.format(Date(time))}"
            }
        }

        btnEndTime.setOnClickListener {
            showDateTimePicker { time ->
                endTime = time
                btnEndTime.text = "End: ${dateFormat.format(Date(time))}"
            }
        }

        btnAddPhoto.setOnClickListener {
            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickIntent, 100)
        }

        btnSaveExpense.setOnClickListener {
            val amount = etAmount.text.toString()
            val description = etDescription.text.toString()

            if (amount.isNotEmpty()) {
                val expense = Expense(
                    category = category.toString(),
                    amount = amount.toDouble(),
                    description = description,
                    startTime = startTime,
                    endTime = endTime,
                    photoUri = ivPhoto?.toString()
                )
                expenseViewModel.insert(expense)
                Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    private fun showDateTimePicker(onTimeSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            TimePickerDialog(this, { _, hour, minute ->
                calendar.set(year, month, day, hour, minute)
                onTimeSelected(calendar.timeInMillis)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            ivPhoto.setImageURI(imageUri)
        }
    }
}
