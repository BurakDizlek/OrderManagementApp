package com.bd.data.extensions

fun Long?.orZero(): Long {
    return this ?: 0
}