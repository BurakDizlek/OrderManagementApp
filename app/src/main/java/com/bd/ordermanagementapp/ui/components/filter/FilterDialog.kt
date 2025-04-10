package com.bd.ordermanagementapp.ui.components.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bd.data.extensions.ensureEndOfDay
import com.bd.data.extensions.ensureStartOfDay
import com.bd.data.extensions.formatMillisDate
import com.bd.data.extensions.orZero
import com.bd.data.model.order.OrderStatus
import com.bd.data.model.order.toName
import com.bd.ordermanagementapp.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun FilterDialog(
    initialStatuses: Set<OrderStatus>,
    initialFromTime: Long?,
    initialToTime: Long?,
    onDismiss: () -> Unit,
    onApply: (statuses: Set<OrderStatus>, fromTime: Long?, toTime: Long?) -> Unit,
    onClear: () -> Unit
) {
    var tempSelectedStatuses by remember { mutableStateOf(initialStatuses) }
    var tempFromTime by remember { mutableStateOf(initialFromTime) }
    var tempToTime by remember { mutableStateOf(initialToTime) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = tempFromTime,
    )
    val endDateSelectableDates = remember(tempFromTime) {
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return tempFromTime == null || utcTimeMillis >= tempFromTime.orZero()
            }

            override fun isSelectableYear(year: Int): Boolean {
                return true
            }
        }
    }
    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = tempToTime,
        selectableDates = endDateSelectableDates
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.filter_orders_dialog_title)) },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.statuses),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.space_small)))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small)),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OrderStatus.entries.forEach { status ->
                        FilterChip(
                            selected = tempSelectedStatuses.contains(status),
                            onClick = {
                                tempSelectedStatuses = if (tempSelectedStatuses.contains(status)) {
                                    tempSelectedStatuses - status
                                } else {
                                    tempSelectedStatuses + status
                                }
                            },
                            label = {
                                Text(status.toName())
                            }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.order_created_time_range),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.space_small)))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { showStartDatePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            tempFromTime?.formatMillisDate() ?: stringResource(R.string.start_date)
                        )
                    }
                    Spacer(Modifier.width(dimensionResource(R.dimen.space_small)))
                    Text("-", modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(Modifier.width(dimensionResource(R.dimen.space_small)))
                    OutlinedButton(
                        onClick = { showEndDatePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(tempToTime?.formatMillisDate() ?: stringResource(R.string.end_date))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onApply(tempSelectedStatuses, tempFromTime, tempToTime)
                }
            ) {
                Text("Apply")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = onClear) {
                    Text(text = stringResource(R.string.clear_all))
                }
                Spacer(Modifier.width(4.dp))
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        }
    )

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    tempFromTime =
                        startDatePickerState.selectedDateMillis?.ensureStartOfDay()
                    if (tempFromTime != null && tempToTime != null && tempFromTime!! > tempToTime!!) {
                        tempToTime = null
                        endDatePickerState.selectedDateMillis = null
                    }
                    showStartDatePicker = false
                }) { Text(text = stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showStartDatePicker = false
                }) { Text(text = stringResource(R.string.cancel)) }
            }
        ) {
            DatePicker(state = startDatePickerState)
        }
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    tempToTime = endDatePickerState.selectedDateMillis?.ensureEndOfDay()
                    if (tempFromTime != null && tempToTime != null && tempToTime!! < tempFromTime!!) {
                        tempToTime = null
                    }
                    showEndDatePicker = false
                }) { Text(text = stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEndDatePicker = false
                }) { Text(text = stringResource(R.string.cancel)) }
            }
        ) {
            DatePicker(state = endDatePickerState)
        }
    }
}