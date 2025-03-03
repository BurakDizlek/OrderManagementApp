package com.bd.data

fun concatenateUrl(base: String, path: String): String {
    return if (path.startsWith("/")) {
        base + path
    } else {
        "$base/$path"
    }
}