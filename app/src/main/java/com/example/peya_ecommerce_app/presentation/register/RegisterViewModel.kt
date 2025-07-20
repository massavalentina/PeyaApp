package com.example.peya_ecommerce_app.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peya_ecommerce_app.data.repository.UserRepository
import com.example.peya_ecommerce_app.domain.utils.HashUtils // Importe la función de hash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository // Inyección automática del UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

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
                fullNameError = if (!nameValid) "Nombre requerido" else null,
                emailError = if (!emailValid) "Email inválido" else null,
                passwordError = if (!passwordValid) "Al menos 8 caracteres" else null,
                confirmPasswordError = if (!confirmValid) "No coincide" else null,
                isRegisterEnabled = emailValid && passwordValid && confirmValid && nameValid
            )
        }
    }

    fun onRegisterClicked() {
        Log.d("RegisterFlow", "Registro iniciado")

        _uiState.update { it.copy(isLoading = true)}

        viewModelScope.launch {
            try {
                val hashedPassword = HashUtils.hashPassword(_uiState.value.password)
                Log.d("RegisterFlow", "Contraseña encriptada: $hashedPassword")

                val result = userRepository.registerUser(
                    fullName = _uiState.value.fullName,
                    email = _uiState.value.email,
                    password = hashedPassword
                )
                Log.d("RegisterFlow", "Llamada al repositorio realizada")

                result.onSuccess {
                    Log.d("RegisterFlow", "Registro exitoso: ")

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRegistered = true,
                            globalError = null
                        )
                    }
                }.onFailure { error ->
                    Log.d("RegisterFlow", "Error en el registro: ${error.localizedMessage}")
                    showError(error.message.toString())
                }
            } catch (e: Exception) {
                Log.d("RegisterFlow", "Excepción inesperada: ${e.localizedMessage}")

                showError(e.message ?: "Error inesperado")
            }
        }
    }

    private fun showError(errorMessage: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isRegistered = false,
                globalError = errorMessage // Mostrar mensaje de error
            )
        }
    }

    fun clearGlobalError() {
        _uiState.update { it.copy(globalError = null) }
    }

    fun resetRegisterState() {
        _uiState.update {
            it.copy(
                isRegistered = false,
                globalError = null,
                fullName = "",
                email = "",
                password = "",
                confirmPassword = ""
            )
        }
    }
}
