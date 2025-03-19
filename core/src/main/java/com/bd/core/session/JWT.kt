package com.bd.core.session

import com.google.gson.annotations.SerializedName

data class JWT(
    val unit: String,
    val username: String
)