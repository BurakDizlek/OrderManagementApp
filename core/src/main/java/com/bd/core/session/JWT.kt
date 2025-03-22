package com.bd.core.session

data class JWT(
    val unit: String,
    val username: String,
    val mobileNumber: String,
    val commonName: String,
    val exp: Long,
)