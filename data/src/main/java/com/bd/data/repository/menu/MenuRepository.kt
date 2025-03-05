package com.bd.data.repository.menu

import com.bd.data.model.BasePaginatedData
import com.bd.data.model.BaseResult
import com.bd.data.model.MenuItem

interface MenuRepository {
    suspend fun getPaginatedMenuItems(
        page: Int,
        pageSize: Int
    ): BaseResult<BasePaginatedData<MenuItem>>

    suspend fun getMenuItemByIds(ids: String): BaseResult<List<MenuItem>>
}