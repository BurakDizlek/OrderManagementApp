package com.bd.network

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class HttpLogger : Logger {
    override fun log(message: String) {
        Log.d("HttpLogger", message)
    }
}