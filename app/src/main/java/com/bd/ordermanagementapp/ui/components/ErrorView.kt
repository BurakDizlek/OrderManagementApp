package com.bd.ordermanagementapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.extensions.mediumPadding

/**
 * A composable function that displays an error view with an error message and a retry button.
 *
 * @param errorMessage The error message to be displayed.
 * @param retryOnClick A lambda function that will be invoked when the retry button is clicked.
 *
 * This view is typically used to display error states and provide a way for the user to retry the operation.
 */
@Composable
fun ErrorView(errorMessage: String, retryOnClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.space_small)
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.space_large))
    ) {
        Column {
            Text(
                text = stringResource(R.string.error_view_title),
                modifier = Modifier.mediumPadding(),
                textAlign = TextAlign.Center,
            )

            Text(
                text = errorMessage,
                modifier = Modifier.mediumPadding(),
                textAlign = TextAlign.Center,
            )

            OutlinedButton(modifier = Modifier
                .fillMaxWidth()
                .mediumPadding(includeBottom = true)
                .wrapContentSize(Alignment.BottomEnd), onClick = {
                retryOnClick.invoke()
            }) {
                Text(stringResource(R.string.error_view_try_again))
            }
        }
    }
}