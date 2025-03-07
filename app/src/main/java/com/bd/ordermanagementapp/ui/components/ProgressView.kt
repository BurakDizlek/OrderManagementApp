package com.bd.ordermanagementapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bd.ordermanagementapp.R

/**
 * A composable function that displays a circular progress indicator.
 * The indicator is centered and takes up the full width of its container.
 */
@Composable
fun ProgressView() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(vertical = dimensionResource(R.dimen.space_large))
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    )
}