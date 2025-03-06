package com.bd.data

fun concatenateUrl(base: String, path: String): String {
    return if (path.startsWith("/")) {
        base + path
    } else {
        "$base/$path"
    }
}

inline fun <reified T : Enum<T>> safeEnumValueOf(type: String?, defaultEnum: T): T {
    return try {
        java.lang.Enum.valueOf(T::class.java, type.orEmpty())
    } catch (e: Exception) {
        defaultEnum
    }
}