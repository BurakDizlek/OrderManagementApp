package com.bd.ordermanagementapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun KeyValue(modifier: Modifier = Modifier, key: String, value: String) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = key,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.5f)
        )
    }
}