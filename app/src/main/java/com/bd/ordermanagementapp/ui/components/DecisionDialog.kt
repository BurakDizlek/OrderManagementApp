package com.bd.ordermanagementapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DecisionDialog(
    title: String,
    message: String,
    rightButtonClick: () -> Unit,
    leftButtonClick: () -> Unit,
    onDismiss: () -> Unit,
    rightButtonText: String,
    leftButtonText: String
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = rightButtonClick) {
                Text(rightButtonText)
            }
        },
        dismissButton = {
            Button(onClick = leftButtonClick) {
                Text(leftButtonText)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DecisionDialogPreview() {
    DecisionDialog(
        title = "Select an Option",
        message = "Choose which action to perform:",
        rightButtonClick = {},
        leftButtonClick = {},
        onDismiss = {},
        rightButtonText = "Option A",
        leftButtonText = "Option B",
    )
}