package com.bd.network.errors

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

suspend inline fun <reified T> handleErrors(
    crossinline response: suspend () -> HttpResponse
): T = withContext(Dispatchers.IO) {

    val result = try {
        response()
    } catch (e: IOException) {
        throw NetworkException(NetworkError.ServiceUnavailable)
    }

    when (result.status.value) {
        in 200..299 -> Unit
        in 400..499 -> throw NetworkException(NetworkError.ClientError)
        500 -> throw NetworkException(NetworkError.ServerError)
        else -> throw NetworkException(NetworkError.UnknownError)
    }

    return@withContext try {
        result.body()
    } catch (e: Exception) {
        throw NetworkException(NetworkError.ServerError)
    }
}