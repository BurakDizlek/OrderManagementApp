package com.bd.ordermanagementapp.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.navigation.NavHostController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.screens.main.GraphRoute
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import com.bd.ordermanagementapp.ui.extensions.largePadding
import com.bd.ordermanagementapp.ui.theme.DustyWhite
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    navController: NavHostController,
) {

    LaunchedEffect(Unit) {
        viewModel.checkLoggedIn()
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ToolbarWithTitle(title = stringResource(R.string.profile_screen_title))
        }, content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(DustyWhite)
            ) {
                Column(
                    modifier = Modifier.align(alignment = Alignment.Center),
                ) {
                    if (uiState.userName == null) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .largePadding(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = Bold,
                            text = stringResource(R.string.you_are_not_logged_in_yet)
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .largePadding(),
                            onClick = {
                                navController.navigate(GraphRoute.LOGIN)
                            }
                        ) {
                            Text(text = stringResource(R.string.login))
                        }
                    } else { // The user logged in
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .largePadding(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = Bold,
                            text = "Welcome ${uiState.userName}"
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .largePadding(),
                            onClick = {
                                viewModel.logout()
                            }
                        ) {
                            Text(text = stringResource(R.string.logout))
                        }
                    }
                }
            }
        }
    )
}