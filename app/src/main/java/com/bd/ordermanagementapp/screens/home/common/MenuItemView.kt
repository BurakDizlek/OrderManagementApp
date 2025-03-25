package com.bd.ordermanagementapp.screens.home.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bd.data.extensions.formatPrice
import com.bd.data.model.MenuItem
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.extensions.smallPadding

@Composable
fun RowScope.MenuItemView(
    item: MenuItem,
    viewModel: HomeCommonViewModel,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.space_small)),
        modifier = modifier
            .fillMaxWidth()
            .weight(0.5f)
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.space_medium))
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            modifier = Modifier
                .size(120.dp)
                .fillMaxWidth()
        )
        Text(text = item.name, modifier = Modifier.smallPadding())

        if (item.firstPrice > 0.0 && item.firstPrice > item.lastPrice) {
            val price = item.firstPrice.formatPrice(currency = item.currency)
            Text(
                text = AnnotatedString(
                    text = price, spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(textDecoration = TextDecoration.LineThrough),
                            start = 0,
                            end = price.length
                        )
                    )
                ), modifier = Modifier.smallPadding()
            )
        } else {
            Text(text = "", modifier = Modifier.smallPadding())
        }

        Text(
            text = item.lastPrice.formatPrice(currency = item.currency),
            modifier = Modifier.smallPadding()
        )

        IconButton(
            onClick = {
                viewModel.onAddButtonClicked(item.id)
            }, modifier = Modifier
                .smallPadding(includeBottom = true)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(1.dp, Color.Gray, CircleShape)
                .align(alignment = Alignment.End)

        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Button")
        }
    }
}