package com.bd.ordermanagementapp.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.bd.ordermanagementapp.R

@Composable
fun NotificationPermissionHandler(
    onPermissionResult: (Boolean) -> Unit = {},
) {
    // Only relevant for Android 13 (API 33) and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        var showRationaleDialog by remember { mutableStateOf(false) }
        var showSettingsDialog by remember { mutableStateOf(false) }

        // 1. Check current permission status
        var hasNotificationPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        }

        // 2. Prepare the permission launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
                if (!isGranted) {
                    // Optional: Check if rationale should be shown next time OR
                    // if the user selected "Don't ask again".
                    // If shouldShowRequestPermissionRationale returns false after denial,
                    // it means the user likely selected "Don't ask again".
                    // We might want to prompt them to go to settings.
                    if (!shouldShowRequestPermissionRationale(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    ) {
                        showSettingsDialog = true
                    }
                }
                onPermissionResult(isGranted) // Notify caller about the result
            }
        )

        LaunchedEffect(Unit) {
            if (shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                showRationaleDialog = true
            } else {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }


        // 4. Rationale Dialog (Example)
        if (showRationaleDialog) {
            AlertDialog(
                onDismissRequest = { showRationaleDialog = false },
                title = { Text(stringResource(id = R.string.notification_permission_rationale_dialog_title)) },
                text = { Text(stringResource(id = R.string.notification_permission_rationale_dialog_text)) },
                confirmButton = {
                    Button(onClick = {
                        showRationaleDialog = false
                        // Request permission after showing rationale
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }) {
                        Text(stringResource(id = R.string.notification_permission_allow_button))
                    }
                },
                dismissButton = {
                    Button(onClick = { showRationaleDialog = false }) {
                        Text(stringResource(id = R.string.notification_permission_maybe_later_button))
                    }
                }
            )
        }

        if (showSettingsDialog) {
            AlertDialog(
                onDismissRequest = { showSettingsDialog = false },
                title = { Text(stringResource(R.string.notification_permission_settings_dialog_title)) },
                text = { Text(stringResource(R.string.notification_permission_settings_dialog_text)) },
                confirmButton = {
                    Button(onClick = {
                        showSettingsDialog = false
                        // Open app settings
                        openAppSettings(context)
                    }) {
                        Text(stringResource(R.string.notification_permission_open_settings_button))
                    }
                },
                dismissButton = {
                    Button(onClick = { showSettingsDialog = false }) {
                        Text(stringResource(R.string.notification_permission_cancel_button))
                    }
                }
            )
        }
    } else {
        LaunchedEffect(Unit) { onPermissionResult(true) }
    }
}

// Helper function to check if rationale should be shown (requires Activity context usually)
// For simplicity in composable, we might just check PackageManager, but Activity check is more accurate
// This basic version works for the initial check. Activity provides more accurate check after denial.
private fun shouldShowRequestPermissionRationale(context: Context, permission: String): Boolean {
    // Note: ActivityCompat.shouldShowRequestPermissionRationale requires an Activity.
    // This basic check might suffice for the initial prompt logic, but handling
    // the "Don't Ask Again" case accurately often involves checking this from an Activity context
    // or relying on the callback logic as shown in the permissionLauncher.
    // For this example, we primarily rely on the launcher's result callback logic.
    return false // Simplification: Assume rationale isn't needed on first click unless handled via activity
}

// Helper function to open app settings
private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}