package com.example.peya_ecommerce_app.presentation.screen.productlist.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoginEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val globalError: String? = null
)