package com.example.peya_ecommerce_app.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.peya_ecommerce_app.data.local.UserPreferences
import com.example.peya_ecommerce_app.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userPreferences: UserPreferences // DataStore para manejar la sesión persistida
) : ViewModel() {

    // Detectar si el usuario está logueado
    val isLoggedIn: Flow<Boolean> = userPreferences.isLoggedIn

    // Logout: Limpiar los datos del usuario
    fun logout(navController: NavController) {
        viewModelScope.launch {
            userPreferences.clearUser() // Borra los datos persistidos
            Log.d("LogoutFlow", "Datos del usuario eliminados de DataStore")

            navController.navigate(Screen.Login.route) { // Redirige al Login
                popUpTo(Screen.ProductList.route) { inclusive = true } // Limpia el stack de navegación
            }
        }
    }
}