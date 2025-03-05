package com.bd.data.extensions

fun Int?.orZero(): Int {
    return this ?: 0
}