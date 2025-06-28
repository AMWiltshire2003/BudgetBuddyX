package com.example.expensetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.data.Badge
//import com.example.expensetracker.db.Badge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BadgeAdapter : ListAdapter<Badge, BadgeAdapter.BadgeViewHolder>(BadgeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_badge, parent, false)
        return BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val badge = getItem(position)
        holder.bind(badge)
    }

    class BadgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(badge: Badge) {
            itemView.findViewById<TextView>(R.id.badgeName).text = badge.name
            itemView.findViewById<TextView>(R.id.badgeDescription).text = badge.description
        }
    }
}

class BadgeDiffCallback : DiffUtil.ItemCallback<Badge>() {
    override fun areItemsTheSame(oldItem: Badge, newItem: Badge): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Badge, newItem: Badge): Boolean = oldItem == newItem
}


// Example usage in another activity (e.g., after adding expense)
fun checkForBadge(context: Context) {
    val db = AppDatabase.getInstance(context)
    CoroutineScope(Dispatchers.IO).launch {
        val count = db.expenseDao().getExpensesCountToday()
        if (count >= 1) {
            db.badgeDao().insertBadge(
                Badge(
                    name = "First Expense!",
                    description = "Logged your first expense today!",
                    earnedDate = System.currentTimeMillis()
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "🏅 You earned a new badge!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
