package com.bd.data.extensions

import com.bd.data.model.BaseResult
import com.bd.data.model.ResultCodes.NO_CODE
import com.bd.network.model.BaseResponse

fun <T, U> BaseResponse<T>.toBaseResult(mapper: (T?) -> U): BaseResult<U> {
    return BaseResult(
        data = mapper(this.data),
        message = message.orEmpty(),
        code = code ?: NO_CODE
    )
}