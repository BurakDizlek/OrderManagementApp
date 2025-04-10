package com.bd.ordermanagementapp.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.ui.components.ProgressView
import com.bd.ordermanagementapp.ui.components.ToolbarWithTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigationController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            ToolbarWithTitle(
                title = stringResource(R.string.login_screen_title),
                navigationControllerToPopBack = navigationController
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(R.string.login_username)) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_small)))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(R.string.login_password)) },
                    visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_medium)))
                Button(onClick = { viewModel.login(username, password) }) {
                    Text(text = stringResource(R.string.login))
                }

                if (uiState.loading) {
                    ProgressView()
                }

                uiState.successMessage?.let {
                    Text(text = it)
                }

                uiState.loggedInUserType?.let {
                    scope.launch {
                        delay(1000)
                        navigationController.popBackStack()
                    }
                }

                uiState.errorMessage?.let {
                    Text(text = it)
                }
            }
        })
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(navigationController = rememberNavController())
}