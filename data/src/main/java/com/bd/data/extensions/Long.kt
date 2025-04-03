package com.bd.data.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long?.orZero(): Long {
    return this ?: 0
}

//Date utils
fun Long.formatMillisDate(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", locale)
    sdf.timeZone = TimeZone.getTimeZone("Europe/Istanbul")
    return sdf.format(Date(this))
}

fun Long.formatMillisDateSmart(locale: Locale = Locale.getDefault()): String {
    val timeZone = TimeZone.getTimeZone("Europe/Istanbul")
    val inputCalendar = Calendar.getInstance(timeZone, locale)
    inputCalendar.timeInMillis = this
    val nowCalendar = Calendar.getInstance(timeZone, locale)
    val isSameDay = inputCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR) &&
            inputCalendar.get(Calendar.DAY_OF_YEAR) == nowCalendar.get(Calendar.DAY_OF_YEAR)
    val pattern: String = if (isSameDay) {
        "HH:mm"
    } else {
        "dd.MM.yyyy HH:mm"
    }
    val sdf = SimpleDateFormat(pattern, locale)
    sdf.timeZone = timeZone
    return sdf.format(Date(this))
}

fun Long.ensureStartOfDay(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"))
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Long.ensureEndOfDay(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"))
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}