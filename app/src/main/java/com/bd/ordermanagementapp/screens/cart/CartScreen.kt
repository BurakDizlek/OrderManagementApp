package com.bd.ordermanagementapp.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bd.data.extensions.formatPrice
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.extensions.mediumPadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel


@Composable
fun CartScreen(viewModel: CartViewModel = koinViewModel(), padding: PaddingValues) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCart()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Scaffold(
            topBar = {
                ToolbarWithTitle(title = stringResource(R.string.my_cart_title))
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(DustyWhite)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            itemsIndexed(state.cart?.cartItems.orEmpty()) { index, cartItem ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .mediumPadding()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .mediumPadding(includeBottom = true)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(3f)
                                        ) { //Image - add and remove buttons
                                            AsyncImage(
                                                model = cartItem.menuItem.imageUrl,
                                                contentDescription = cartItem.menuItem.name,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .height(80.dp)
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .border(
                                                        1.dp,
                                                        Color.LightGray,
                                                        shape = RectangleShape
                                                    )
                                                    .fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        viewModel.deleteFromCart(cartItem.menuItem.id)
                                                    }, modifier = Modifier
                                                        .weight(1f)
                                                        .background(Color.LightGray)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(R.drawable.ic_remove_circle_outline_24),
                                                        contentDescription = "Remove Button"
                                                    )
                                                }

                                                Text(
                                                    text = cartItem.quantity.toString(),
                                                    style = MaterialTheme.typography.titleMedium,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .weight(2f)
                                                        .fillMaxSize()
                                                )

                                                IconButton(
                                                    onClick = {
                                                        viewModel.addToCart(cartItem.menuItem.id)
                                                    }, modifier = Modifier
                                                        .weight(1f)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(R.drawable.ic_add_circle_outline_24),
                                                        contentDescription = "Add Button"
                                                    )
                                                }
                                            }
                                        }

                                        Column(
                                            modifier = Modifier
                                                .weight(4f)
                                                .wrapContentHeight()
                                        ) { // name, price etc...
                                            Text(
                                                text = cartItem.menuItem.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .mediumPadding()
                                            )

                                            val quantityText =
                                                if (cartItem.quantity > 1) " X ${cartItem.quantity}" else ""

                                            Text(
                                                text = cartItem.menuItem.lastPrice.formatPrice(
                                                    cartItem.menuItem.currency
                                                ) + quantityText,
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .mediumPadding()
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        state.cart?.let { cart ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Row(modifier = Modifier.mediumPadding()) {
                                        Text(
                                            text = stringResource(R.string.total_price_label),
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                        Text(
                                            text = cart.totalPrice.formatPrice(cart.currency),
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                    }

                                    Button(
                                        onClick = {}, modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(horizontal = dimensionResource(R.dimen.space_small))
                                    ) {
                                        Text(stringResource(R.string.order))
                                    }
                                }
                            }
                        }
                    }

                    if (state.loadingCart) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        )

    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    CartScreen(padding = PaddingValues(16.dp))
}