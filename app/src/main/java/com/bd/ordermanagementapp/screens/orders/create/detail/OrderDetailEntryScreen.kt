package com.bd.ordermanagementapp.screens.orders.create.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.create.CreateOrderRoute
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun OrderDetailEntryScreen(
    viewModel: OrderDetailEntryViewModel = koinViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    var note by rememberSaveable { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.getAddress()
    }

    OrderManagementAppTheme {
        Scaffold(
            topBar = {
                ToolbarWithTitle(
                    stringResource(R.string.order_detail_entry_title),
                    navigationControllerToPopBack = navController
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }, content = { padding ->
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .largePadding(),
                            value = uiState.address,
                            onValueChange = { viewModel.onAddressChanged(it) },
                            label = { Text(text = stringResource(R.string.address)) },
                            minLines = 3,
                            maxLines = 5,
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_small)))
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimensionResource(R.dimen.space_large)),
                            value = note,
                            onValueChange = { note = it },
                            label = { Text(text = stringResource(R.string.note)) },
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_medium)))
                        Button(
                            enabled = uiState.address.isNotEmpty(),
                            onClick = {
                                viewModel.createOrder(note = note)
                            }) {
                            Text(text = stringResource(R.string.complete))
                        }
                    }

                    if (uiState.loading) {
                        ProgressView(Modifier.align(Alignment.Center))
                    }

                    uiState.errorMessage?.let {
                        scope.launch {
                            keyboardController?.hide()
                            snackBarHostState.showSnackbar(message = it)
                            viewModel.clearCreateOrderErrorMessage()
                        }
                    }

                    uiState.createdOrder?.let {
                        viewModel.navigated()
                        navController.navigate(CreateOrderRoute.Success(it.id))
                    }
                }
            }
        )
    }
}