package com.example.expensetracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.Expense
import com.example.expensetracker.data.ExpenseViewModel
import com.example.expensetracker.util.AppSettings
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddExpenseActivity : BaseActivity() {

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
                val symbol = AppSettings.getCurrencySymbol(this)
                Toast.makeText(this, "$symbol ${etAmount.text} saved!", Toast.LENGTH_SHORT).show()
                checkForBadge()
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

    private fun checkForBadge() {
        val db = com.example.expensetracker.data.AppDatabase.getInstance(this)
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            val count = db.expenseDao().getExpensesCountToday()
            if (count == 1) { // First expense of the day
                db.badgeDao().insertBadge(
                    com.example.expensetracker.data.Badge(
                        name = "First Expense!",
                        description = "Logged your first expense today!",
                        earnedDate = System.currentTimeMillis()
                    )
                )
                withContext(kotlinx.coroutines.Dispatchers.Main) {
                    Toast.makeText(this@AddExpenseActivity, "🏅 You earned a new badge!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
