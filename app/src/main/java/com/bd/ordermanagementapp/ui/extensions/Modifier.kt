package com.bd.ordermanagementapp.ui.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bd.ordermanagementapp.R


/**
 * Applies a small padding to the Modifier.
 *
 * This function adds a uniform padding of `space_small` to the top, start, and end of the
 * Modifier. It provides an option to include or exclude bottom padding.
 *
 * @param includeBottom `true` to include `space_small` padding at the bottom, `false` to have no bottom padding.
 *                       Defaults to `false`.
 * @return A new [Modifier] with the applied padding.
 *
 * Example Usage:
 *
 * ```kotlin
 * // With no bottom padding
 * Box(modifier = Modifier.smallPadding()) {
 *     Text("Hello")
 * }
 *
 * // With bottom padding
 * Box(modifier = Modifier.smallPadding(includeBottom = true)) {
 *     Text("Hello")
 * }
 * ```
 *
 * Note:
 * - `space_small` and `space_none` are expected to be defined in the `dimens.xml` resource file.
 * - `space_none` is assumed to be zero, in order to remove the bottom padding
 */
@Composable
fun Modifier.smallPadding(includeBottom: Boolean = false): Modifier {
    return this.then(
        Modifier.padding(
            top = dimensionResource(R.dimen.space_small),
            start = dimensionResource(R.dimen.space_small),
            end = dimensionResource(R.dimen.space_small),
            bottom = dimensionResource(if (includeBottom) R.dimen.space_small else R.dimen.space_none)
        )
    )
}

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