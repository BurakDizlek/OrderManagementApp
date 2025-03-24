package com.bd.ordermanagementapp.screens.orders.create.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import com.bd.ordermanagementapp.ui.components.ErrorView
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun OrderDetailEntryScreen(
    viewModel: OrderDetailEntryViewModel = koinViewModel(),
    navController: NavController,
) {
    val uiState = viewModel.uiState.collectAsState()
    var address by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    OrderManagementAppTheme {
        Scaffold(
            topBar = {
                ToolbarWithTitle(
                    stringResource(R.string.order_detail_entry_title),
                    navigationControllerToPopBack = navController
                )
            }, content = { padding ->
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text(text = stringResource(R.string.address)) }
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_small)))
                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text(text = stringResource(R.string.note)) },
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_medium)))
                        Button(
                            enabled = address.isNotEmpty(),
                            onClick = {
                                viewModel.createOrder(
                                    address = address,
                                    note = note
                                )
                            }) {
                            Text(text = stringResource(R.string.complete))
                        }
                    }

                    if (uiState.value.loading) {
                        ProgressView()
                    }

                    uiState.value.errorMessage?.let {
                        ErrorView(errorMessage = it) {
                            viewModel.createOrder(address, note)
                        }
                    }

                    uiState.value.createdOrder?.let {
                        viewModel.navigated()
                        navController.navigate(CreateOrderRoute.Success(it.id))
                    }
                }
            }
        )
    }
}