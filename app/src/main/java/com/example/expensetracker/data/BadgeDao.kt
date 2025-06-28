package com.example.expensetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BadgeDao {
    @Insert
    suspend fun insertBadge(badge: Badge)

    @Query("SELECT * FROM badges ORDER BY earnedDate DESC")
    suspend fun getAllBadges(): List<Badge>

    @Query("SELECT COUNT(*) FROM badges")
    suspend fun getBadgeCount(): Int

    @Query("SELECT COUNT(*) FROM badges WHERE name = 'Streak' AND earnedDate > :timestamp")
    suspend fun getStreakSince(timestamp: Long): Int

    @Query("SELECT COUNT(*) FROM badges WHERE name = 'Streak'")
    suspend fun getStreak(): Int
}

