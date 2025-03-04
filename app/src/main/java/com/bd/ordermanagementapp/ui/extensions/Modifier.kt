package com.bd.ordermanagementapp.ui.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bd.ordermanagementapp.R


/**
 * An extension function for [Modifier] to apply a medium padding on the top, start, and end,
 * with optional padding on the bottom.
 *
 * @param includeBottom Determines whether to include medium padding at the bottom.
 *                      If true, medium padding is applied to the bottom;
 *                      otherwise, no padding is applied.
 */
@Composable
fun Modifier.mediumPadding(includeBottom: Boolean = false): Modifier {
    return this.then(
        Modifier.padding(
            top = dimensionResource(R.dimen.space_medium),
            start = dimensionResource(R.dimen.space_medium),
            end = dimensionResource(R.dimen.space_medium),
            bottom = dimensionResource(if (includeBottom) R.dimen.space_medium else R.dimen.space_none)
        )
    )
}

/**
 * An extension function for [Modifier] to apply an large padding on the top, start, and end,
 * with optional padding on the bottom.
 *
 * @param includeBottom Determines whether to include large padding at the bottom.
 *                      If true, large padding is applied to the bottom;
 *                      otherwise, no padding is applied.
 */
@Composable
fun Modifier.largePadding(includeBottom: Boolean = false): Modifier {
    return this.then(
        Modifier.padding(
            top = dimensionResource(R.dimen.space_large),
            start = dimensionResource(R.dimen.space_large),
            end = dimensionResource(R.dimen.space_large),
            bottom = dimensionResource(if (includeBottom) R.dimen.space_large else R.dimen.space_none)
        )
    )
}