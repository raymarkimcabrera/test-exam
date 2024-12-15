package com.example.randomuserapp.core.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.toHumanReadableDate(): String {
    return try {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val date = inputDateFormat.parse(this)

        date?.let { outputDateFormat.format(it) } ?: "Invalid date"
    } catch (e: Exception) {
        "Invalid date"
    }
}