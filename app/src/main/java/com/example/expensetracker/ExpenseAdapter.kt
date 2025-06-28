package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.Expense
import com.example.expensetracker.util.AppSettings
//import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

class ExpenseAdapter(private val expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvDesc: TextView = view.findViewById(R.id.tvDescription)
        val tvTimes: TextView = view.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        val symbol = AppSettings.getCurrencySymbol(holder.itemView.context)
        holder.tvAmount.text = "$symbol ${expense.amount}"
        holder.tvDesc.text = expense.description
        //holder.tvAmount.text = "Amount: $${expense.amount}"
        //holder.tvDesc.text = "Note: ${expense.description}"
        holder.tvTimes.text = "Start: ${dateFormat.format(Date(expense.startTime))} - End: ${dateFormat.format(Date(expense.endTime))}"
    }

    override fun getItemCount(): Int = expenses.size
}
