package com.example.expensetracker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.util.AppSettings
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BudgetGraphActivity : AppCompatActivity() {

    private lateinit var barChart1: BarChart
    private lateinit var barChart2: BarChart
    private lateinit var btnStartDate: Button
    private lateinit var btnEndDate: Button
    private lateinit var tvRange: TextView

    private var startDate: Long = 0
    private var endDate: Long = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_graph)

        barChart1 = findViewById(R.id.barChartMonthlyProgress)
        barChart2 = findViewById(R.id.barChartCategoryBreakdown)
        btnStartDate = findViewById(R.id.btnStartDate)
        btnEndDate = findViewById(R.id.btnEndDate)
        tvRange = findViewById(R.id.tvSelectedRange)

        lifecycleScope.launch {
            loadCategoryGraph()
            loadMonthlyProgressGraph()
        }


        btnStartDate.setOnClickListener {
            showDatePicker { date ->
                startDate = date
                updateDateRangeText()
                lifecycleScope.launch {
                    loadCategoryGraph()
                }
            }
        }

        btnEndDate.setOnClickListener {
            showDatePicker { date ->
                endDate = date
                updateDateRangeText()
                lifecycleScope.launch {
                    loadCategoryGraph()
                }
            }
        }

        lifecycleScope.launch {
            loadMonthlyProgressGraph()
        }

        //  Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_graph

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
                R.id.nav_graph -> true
                R.id.nav_gamification -> {
                    startActivity(Intent(this, BudgetKeeperActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
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

    private fun showDatePicker(callback: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            calendar.set(year, month, day)
            callback(calendar.timeInMillis)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateDateRangeText() {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val text = "From: ${sdf.format(Date(startDate))} To: ${sdf.format(Date(endDate))}"
        tvRange.text = text
    }

    private fun getMinGoal(): Float {
        return 100f  // You can load this from user preferences
    }

    private fun getMaxGoal(): Float {
        return 500f  // You can load this from user preferences
    }


    private suspend fun loadMonthlyProgressGraph() {
        val db = AppDatabase.getInstance(this)
        val expenses = db.expenseDao().getLast30DaysExpenses(System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000))

        val dailySums = mutableMapOf<String, Double>()
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())

        for (expense in expenses) {
            val day = sdf.format(Date(expense.startTime))
            dailySums[day] = dailySums.getOrDefault(day, 0.0) + expense.amount
        }

        val entries = dailySums.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }
        val labels = dailySums.keys.toList()
        val barDataSet = BarDataSet(entries, "Daily Spending")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val data = BarData(barDataSet)

        barChart1.data = data
        barChart1.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        val minGoal = AppSettings.getMinGoal(applicationContext)
        val maxGoal = AppSettings.getMaxGoal(applicationContext)

        barChart1.axisLeft.removeAllLimitLines()
        barChart1.axisLeft.addLimitLine(LimitLine(minGoal, "Min Goal"))
        barChart1.axisLeft.addLimitLine(LimitLine(maxGoal, "Max Goal"))

        barChart1.invalidate()
    }

    private suspend fun loadCategoryGraph() {
        if (startDate == 0L || endDate == 0L || endDate <= startDate) return

        val db = AppDatabase.getInstance(this)
        val expenses = db.expenseDao().getExpensesBetween(startDate, endDate)

        val categoryTotals = mutableMapOf<String, Double>()
        for (expense in expenses) {
            categoryTotals[expense.category] = categoryTotals.getOrDefault(expense.category, 0.0) + expense.amount
        }

        val entries = categoryTotals.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }
        val labels = categoryTotals.keys.toList()
        val barDataSet = BarDataSet(entries, "Category Spending")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val data = BarData(barDataSet)

        barChart2.data = data
        barChart2.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        val minGoal = AppSettings.getMinGoal(applicationContext)
        val maxGoal = AppSettings.getMaxGoal(applicationContext)

        barChart2.axisLeft.removeAllLimitLines()
        barChart2.axisLeft.addLimitLine(LimitLine(minGoal, "Min Goal"))
        barChart2.axisLeft.addLimitLine(LimitLine(maxGoal, "Max Goal"))

        barChart2.invalidate()

        val chart = findViewById<BarChart>(R.id.barChartMonthlyProgress)
        chart.setFitBars(true)

    }
}