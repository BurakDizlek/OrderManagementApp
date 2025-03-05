package com.bd.network.service.menu

import com.bd.network.model.BaseResponse
import com.bd.network.model.MenuItemDto
import com.bd.network.model.PaginatedResponse


interface MenuService {
    suspend fun getPaginatedMenuItems(
        page: Int,
        pageSize: Int
    ): BaseResponse<PaginatedResponse<MenuItemDto>>

    suspend fun getMenuItemByIds(ids: String): BaseResponse<List<MenuItemDto>>
}