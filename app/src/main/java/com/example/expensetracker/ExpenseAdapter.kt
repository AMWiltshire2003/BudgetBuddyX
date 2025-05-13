package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.Expense
import java.sql.Date

class ExpenseAdapter(private val expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvTimes: TextView = view.findViewById(R.id.tvTimes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.tvAmount.text = "Amount: $${expense.amount}"
        holder.tvDesc.text = "Note: ${expense.description}"
        holder.tvTimes.text = "Start: ${Date(expense.startTime)} - End: ${Date(expense.endTime)}"
    }

    override fun getItemCount(): Int = expenses.size
}
