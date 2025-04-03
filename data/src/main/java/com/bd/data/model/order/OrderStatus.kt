package com.bd.data.model.order

import com.bd.data.model.order.OrderStatus.entries

enum class OrderStatus {
    OPEN,
    ON_THE_WAY,
    COMPLETED,
    CANCELED,
    REJECTED;

    companion object {

        fun fromValue(value: String?): OrderStatus? {
            return entries.find { it.name == value?.uppercase() }
        }

        fun fromValueOrDefault(value: String?): OrderStatus {
            return fromValue(value) ?: OPEN
        }
    }
}