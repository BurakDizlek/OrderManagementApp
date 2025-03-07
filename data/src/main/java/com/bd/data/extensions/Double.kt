package com.bd.data.extensions

import java.util.Locale

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Double.formatPrice(currency: String): String {
    val formattedPrice = String.format(Locale.getDefault(), "%.2f", this)
    return "$formattedPrice $currency"
}