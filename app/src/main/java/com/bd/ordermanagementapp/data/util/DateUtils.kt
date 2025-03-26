package com.bd.ordermanagementapp.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatMillisDate(millis: Long, locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", locale)
    sdf.timeZone = TimeZone.getTimeZone("Europe/Istanbul")
    return sdf.format(Date(millis))
}

fun ensureStartOfDay(millis: Long): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"))
    calendar.timeInMillis = millis
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun ensureEndOfDay(millis: Long): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"))
    calendar.timeInMillis = millis
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}