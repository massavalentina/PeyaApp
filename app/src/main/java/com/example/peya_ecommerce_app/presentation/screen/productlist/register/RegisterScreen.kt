package com.example.peya_ecommerce_app.presentation.screen.productlist.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.peya_ecommerce_app.presentation.navigation.Screen

@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registro", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.fullName,
                onValueChange = { viewModel.onFullNameChanged(it) },
                label = { Text("Nombre completo") },
                isError = uiState.fullNameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.fullNameError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                isError = uiState.emailError != null,
                modifier = Modifier.fillMaxWidth()
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
                isError = uiState.passwordError != null,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.passwordError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.confirmPasswordError != null,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.confirmPasswordError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = { viewModel.onRegisterClicked() },
                    enabled = uiState.isRegisterEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }
            }
        }
    }

    if (uiState.isRegistered) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Registro exitoso") },
            text = { Text("Se envió un correo de validación. Por favor, revisa tu bandeja de entrada.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetRegisterState()
                    navController.navigate(Screen.EmailVerify.route)
                }) {
                    Text("Continuar")
                }
            }
        )
    }

    uiState.globalError?.let { errorMessage ->
        AlertDialog(
            onDismissRequest = { viewModel.clearGlobalError() },
            title = { Text("Error en el registro") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { viewModel.clearGlobalError() }) {
                    Text("Intentar otra vez")
                }
            }
        )
    }
}
