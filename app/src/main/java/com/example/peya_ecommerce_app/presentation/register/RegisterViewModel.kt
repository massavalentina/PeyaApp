package com.example.peya_ecommerce_app.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val users = mutableListOf<Pair<String, String>>()

    private val validEmailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")

    fun onFullNameChanged(value: String) {
        _uiState.update { it.copy(fullName = value) }
        validateForm()
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value) }
        validateForm()
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value) }
        validateForm()
    }

    fun onConfirmPasswordChanged(value: String) {
        _uiState.update { it.copy(confirmPassword = value) }
        validateForm()
    }

    private fun validateForm() {
        val emailValid = validEmailRegex.matches(_uiState.value.email)
        val passwordValid = _uiState.value.password.length >= 8
        val confirmValid = _uiState.value.password == _uiState.value.confirmPassword
        val nameValid = _uiState.value.fullName.isNotBlank()

        _uiState.update {
            it.copy(
                fullNameError = if (!nameValid && it.fullName.isNotBlank()) "Nombre requerido" else null,
                emailError = if (!emailValid && it.email.isNotBlank()) "Email inválido" else null,
                passwordError = if (!passwordValid && it.password.isNotBlank()) "Al menos 8 caracteres" else null,
                confirmPasswordError = if (!confirmValid && it.confirmPassword.isNotBlank()) "No coincide" else null,
                isRegisterEnabled = emailValid && passwordValid && confirmValid && nameValid
            )
        }
    }

    fun onRegisterClicked() {
        users.add(_uiState.value.email to _uiState.value.password)
        _uiState.update { it.copy(isRegistered = true) }
    }
}
