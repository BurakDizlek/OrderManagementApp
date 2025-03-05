package com.bd.data.extensions

fun Double?.orZero(): Double {
    return this ?: 0.0
}