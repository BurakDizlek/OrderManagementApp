package com.bd.ordermanagementapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bd.ordermanagementapp.R

@Composable
fun ErrorDialog(
    errorMessage: String,
    onDismiss: () -> Unit,
    title: String = stringResource(R.string.error_dialog_title),
    confirmButtonText: String = stringResource(R.string.error_dialog_confirm)
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(errorMessage) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(confirmButtonText)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog(
        errorMessage = "An error occurred. Please try again later.",
        onDismiss = {}
    )
}