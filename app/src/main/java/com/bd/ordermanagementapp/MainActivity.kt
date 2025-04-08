package com.bd.ordermanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bd.ordermanagementapp.screens.main.MainScreen
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

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