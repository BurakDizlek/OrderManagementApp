package com.bd.ordermanagementapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.extensions.mediumPadding


@Composable
fun BannerCard(
    imageUrl: String,
    cardTitle: String,
    description: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .mediumPadding(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                R.dimen.space_small
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Banner image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.mediumPadding(includeBottom = true)
            ) {
                Text(
                    text = cardTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.space_small))
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                )
            }
        }
    }
}