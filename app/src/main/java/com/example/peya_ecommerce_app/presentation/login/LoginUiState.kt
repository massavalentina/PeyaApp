package com.example.peya_ecommerce_app.presentation.login

data class LoginUiState( //otra opción para manejar esquemas (!)definir)
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoginEnabled: Boolean = false,
    val isLoggedIn: Boolean = false
)
