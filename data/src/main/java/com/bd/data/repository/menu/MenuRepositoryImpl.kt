package com.bd.data.repository.menu

import com.bd.data.extensions.orZero
import com.bd.data.model.BasePaginatedData
import com.bd.data.model.BaseResult
import com.bd.data.model.MenuItem
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.data.model.toMenuItem
import com.bd.network.service.menu.MenuService

class MenuRepositoryImpl(private val menuService: MenuService) : MenuRepository {
    override suspend fun getPaginatedMenuItems(
        page: Int,
        pageSize: Int
    ): BaseResult<BasePaginatedData<MenuItem>> {
        val response = menuService.getPaginatedMenuItems(page = page, pageSize = pageSize)

        val paginatedData = BasePaginatedData(
            items = response.data?.items?.map {
                it.toMenuItem()
            }.orEmpty(),
            page = response.data?.page.orZero(),
            pageSize = response.data?.pageSize.orZero(),
            totalItems = response.data?.totalItems.orZero(),
            totalPages = response.data?.totalPages.orZero()
        )

        val result = BaseResult(
            data = paginatedData,
            message = response.message.orEmpty(),
            code = response.code ?: NO_CODE
        )

        return result
    }

    override suspend fun getMenuItemByIds(ids: String): BaseResult<List<MenuItem>> {
        val response = menuService.getMenuItemByIds(ids = ids)

        val result = BaseResult(
            data = response.data?.map {
                it.toMenuItem()
            }.orEmpty(),
            message = response.message.orEmpty(),
            code = response.code ?: NO_CODE
        )

        return result
    }
}