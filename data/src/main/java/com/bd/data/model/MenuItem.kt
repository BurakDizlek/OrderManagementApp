package com.bd.data.model

import com.bd.data.concatenateUrl
import com.bd.data.safeEnumValueOf
import com.bd.network.NetworkConstants
import com.bd.network.model.MenuItemDto


data class MenuItem(
    val id: Int,
    val barcode: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val lastPrice: Double,
    val firstPrice: Double,
    val currency: String,
    val discount: Double,
    val category: List<Category>
)

fun MenuItemDto.toMenuItem(): MenuItem {
    return MenuItem(
        id = id ?: 0,
        barcode = barcode.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        imageUrl = concatenateUrl(
            base = NetworkConstants.BASE_URL, path = imageUrl.orEmpty()
        ),
        lastPrice = lastPrice ?: 0.0,
        firstPrice = firstPrice ?: 0.0,
        currency = currency.orEmpty(),
        discount = discount ?: 0.0,
        category = category?.map { categoryString ->
            safeEnumValueOf(
                categoryString,
                Category.NOT_DEFINED
            )
        }.orEmpty()
    )
}