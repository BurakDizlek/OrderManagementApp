package com.bd.network.service.menu

import com.bd.network.NetworkConstants.BASE_URL
import com.bd.network.errors.handleErrors
import com.bd.network.model.BaseResponse
import com.bd.network.model.MenuItemDto
import com.bd.network.model.PaginatedResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val MENU_URL = "$BASE_URL/menu"

class MenuServiceImpl(private val client: HttpClient) : MenuService {

    override suspend fun getPaginatedMenuItems(
        page: Int,
        pageSize: Int
    ): BaseResponse<PaginatedResponse<MenuItemDto>> {
        return handleErrors {
            client.get(MENU_URL) {
                url {
                    parameters.append("page", page.toString())
                    parameters.append("pageSize", pageSize.toString())
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun getMenuItemByIds(ids: String): BaseResponse<List<MenuItemDto>> {
        return handleErrors {
            client.get(MENU_URL) {
                url {
                    parameters.append("ids", ids)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }
}