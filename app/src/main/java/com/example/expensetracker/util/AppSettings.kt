package com.example.expensetracker.util

import android.content.Context
import java.util.Locale

object AppSettings {

    private const val PREF_NAME = "settings"
    private const val MIN_GOAL = "min_goal"
    private const val MAX_GOAL = "max_goal"

    fun getMinGoal(context: Context): Float {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(MIN_GOAL, 50f) // default 50
    }

    fun getMaxGoal(context: Context): Float {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(MAX_GOAL, 500f) // default 500
    }


    fun getCurrencySymbol(context: Context): String {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("currency_symbol", "R") ?: "R"
    }

    fun getLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("language_code", "en") ?: "en"
    }

    fun applyLocale(context: Context): Context {
        val langCode = getLanguageCode(context)
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
