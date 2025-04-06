package com.bd.ordermanagementapp.ui.components.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bd.data.model.order.FilterOrderData
import com.bd.data.model.order.OrderStatus
import com.bd.ordermanagementapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OrderFilterComponent(
    modifier: Modifier = Modifier,
    initialFilter: FilterOrderData = FilterOrderData(),
    onFilterChanged: (FilterOrderData) -> Unit
) {
    var query by rememberSaveable { mutableStateOf(initialFilter.query.orEmpty()) }
    var selectedStatuses by rememberSaveable(
        saver = ListSaver(
            OrderStatus::name,
            OrderStatus::fromValue
        )
    ) {
        mutableStateOf(initialFilter.statuses?.toSet() ?: emptySet())
    }
    var fromTimeMillis by rememberSaveable { mutableStateOf(initialFilter.fromTime) }
    var toTimeMillis by rememberSaveable { mutableStateOf(initialFilter.toTime) }

    var showFilterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(query) {
        delay(350) // Wait 350ms after the last query change
        onFilterChanged(
            FilterOrderData(
                query = query.takeIf { it.isNotBlank() },
                fromTime = fromTimeMillis,
                toTime = toTimeMillis,
                statuses = selectedStatuses.toList()
                    .takeIf { it.isNotEmpty() } // Send null if empty
            )
        )
    }

    LaunchedEffect(selectedStatuses, fromTimeMillis, toTimeMillis) {
        onFilterChanged(
            FilterOrderData(
                query = query.takeIf { it.isNotBlank() },
                fromTime = fromTimeMillis,
                toTime = toTimeMillis,
                statuses = selectedStatuses.toList().takeIf { it.isNotEmpty() }
            )
        )
    }

    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.search_orders)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) { // Clear button
                            Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                        }
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.space_small)))

            IconButton(onClick = { showFilterDialog = true }) {
                Icon(Icons.Default.FilterList, contentDescription = "Open Filters")
            }
        }
    }

    if (showFilterDialog) {
        FilterDialog(
            initialStatuses = selectedStatuses,
            initialFromTime = fromTimeMillis,
            initialToTime = toTimeMillis,
            onDismiss = { showFilterDialog = false },
            onApply = { newStatuses, newFrom, newTo ->
                selectedStatuses = newStatuses
                fromTimeMillis = newFrom
                toTimeMillis = newTo
                showFilterDialog = false
            },
            onClear = {
                selectedStatuses = emptySet()
                fromTimeMillis = null
                toTimeMillis = null
                showFilterDialog = false
            }
        )
    }
}

private fun <T : Enum<T>> ListSaver(
    toString: (T) -> String,
    fromString: (String) -> T?
): Saver<MutableState<Set<T>>, List<String>> {
    return Saver(
        save = { state -> state.value.map(toString) },
        restore = { list -> mutableStateOf(list.mapNotNull(fromString).toSet()) }
    )
}