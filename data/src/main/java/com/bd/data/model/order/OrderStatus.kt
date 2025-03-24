package com.bd.data.model.order

enum class OrderStatus {
    OPEN,
    ON_THE_WAY,
    COMPLETED,
    CANCELED,
    REJECTED;

    companion object {
        fun fromValue(value: String?): OrderStatus {
            return when (value?.uppercase()) {
                "OPEN" -> OPEN
                "ON_THE_WAY" -> ON_THE_WAY
                "COMPLETED" -> COMPLETED
                "CANCELED" -> CANCELED
                "REJECTED" -> REJECTED
                else -> OPEN
            }
        }
    }
}