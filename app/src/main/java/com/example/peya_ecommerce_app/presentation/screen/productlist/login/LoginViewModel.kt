package com.example.peya_ecommerce_app.presentation.screen.productlist.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.peya_ecommerce_app.data.local.UserPreferences
import com.example.peya_ecommerce_app.data.repository.UserRepository
import com.example.peya_ecommerce_app.domain.utils.HashUtils
import com.example.peya_ecommerce_app.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, emailError = null, globalError = null) }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value, passwordError = null, globalError = null) }
    }

    fun onLoginClicked(navController: NavController) {
        val email = uiState.value.email
        val password = uiState.value.password

        if (!isEmailValid(email)) {
            _uiState.update { it.copy(emailError = "Email inválido.") }
            return
        }

        if (password.isEmpty() || password.length < 8) {
            _uiState.update { it.copy(passwordError = "Contraseña inválida.") }
            return
        }

        val hashedPassword = HashUtils.hashPassword(password)

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val result = userRepository.authenticateUser(email, hashedPassword)

                result.onSuccess { authResponse ->
                    viewModelScope.launch {
                        userPreferences.saveUser(
                            email = authResponse.email,
                            userId = authResponse.userId
                        )
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            globalError = null
                        )
                    }
                    navController.navigate(Screen.ProductList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }

                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            globalError = error.message ?: "Credenciales inválidas."
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        globalError = e.message ?: "Error inesperado."
                    )
                }
            }
        }
    }

    fun clearGlobalError() {
        _uiState.update { it.copy(globalError = null) }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})$".toRegex()
        return emailRegex.matches(email)
    }
}