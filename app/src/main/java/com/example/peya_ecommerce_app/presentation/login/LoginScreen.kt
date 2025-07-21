package com.example.peya_ecommerce_app.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peya_ecommerce_app.navigation.Screen

@Composable
fun LoginScreen(navController: NavController,
                modifier: Modifier = Modifier,
                viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.emailError != null
        )
        uiState.emailError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.passwordError != null
        )
        uiState.passwordError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Button(
                onClick = {
                    viewModel.onLoginClicked(navController)
                },
                enabled = uiState.isLoginEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }

            TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text("¿No tenés cuenta? Registrate")
            }
        }
    }

    if (uiState.globalError != null) {
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(uiState.globalError) {
            snackbarHostState.showSnackbar(uiState.globalError ?: "Error desconocido")
            viewModel.clearGlobalError()
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }

    if (uiState.isLoggedIn) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.ProductList.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }
}
