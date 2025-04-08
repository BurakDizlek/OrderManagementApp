package com.bd.ordermanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.bd.ordermanagementapp.screens.main.MainScreen
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderManagementAppTheme {
                MainScreen()
            }
        }
        viewModel.registerDevice()
    }
}