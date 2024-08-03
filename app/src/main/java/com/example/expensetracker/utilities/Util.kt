package com.example.expensetracker.utilities

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Util {

//    fun dateToReadFormat(dateInMilliseconds: Long): String {
//
//        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        return formatter.format(dateInMilliseconds)
//    }

    fun dateToReadFormat(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatToDecimalValue(value: Double): String {
        return String.format("%.2f", value)
    }


}

fun Date.plusDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, days)
    return calendar.time
}

fun Date.minusDays(days: Int): Date {
    return this.plusDays(-days)
}


fun Date.toDayFormat(): String {
    val formatter = SimpleDateFormat("dd", Locale.getDefault())
    return formatter.format(this)
}
fun normalizeDate(date: Date): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(date)
}