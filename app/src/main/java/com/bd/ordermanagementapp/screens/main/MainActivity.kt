package com.bd.ordermanagementapp.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderManagementAppTheme {
                MainScreen()
            }
        }
    }
}