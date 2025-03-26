package com.bd.ordermanagementapp.screens.orders.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.orders.filter.OrderFilterComponent
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import com.bd.ordermanagementapp.ui.theme.OrderManagementAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(viewmodel: OrdersViewModel = koinViewModel(), navController: NavHostController) {

    val state = viewmodel.uiState.collectAsState()

    OrderManagementAppTheme {
        Scaffold(
            topBar = {
                ToolbarWithTitle(stringResource(R.string.orders_screen_title))
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .background(DustyWhite)
                ) {
                    OrderFilterComponent(
                        initialFilter = state.value.filterData,
                        onFilterChanged = { newFilter ->
                            viewmodel.onFilterDataChanged(filterOrderData = newFilter)
                        }
                    )

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(5) { index ->
                            Text(
                                "Sample Order Item ${index + 1}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}