package com.example.peya_ecommerce_app.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val validEmailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")

    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
        validateForm()
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
        validateForm()
    }

    private fun validateForm() {
        val emailValid = validEmailRegex.matches(_uiState.value.email)
        val passwordValid = _uiState.value.password.length >= 8

        _uiState.update {
            it.copy(
                emailError = if (!emailValid && it.email.isNotBlank()) "Email inválido" else null,
                passwordError = if (!passwordValid && it.password.isNotBlank()) "Mínimo 8 caracteres" else null,
                isLoginEnabled = emailValid && passwordValid
            )
        }
    }

    fun onLoginClicked() {
        val isCorrectUser =
            _uiState.value.email == "test@test.com" && _uiState.value.password == "12345678"

        _uiState.update {
            it.copy(
                isLoggedIn = isCorrectUser,
                emailError = if (!isCorrectUser) "Credenciales inválidas" else null,
                passwordError = if (!isCorrectUser) "Credenciales inválidas" else null
            )
        }
    }
}
