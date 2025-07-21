package com.example.peya_ecommerce_app.presentation.screen.productlist.register

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isRegisterEnabled: Boolean = false,
    val isRegistered: Boolean = false,
    val isLoading: Boolean = false,
    val globalError: String? = null
)
