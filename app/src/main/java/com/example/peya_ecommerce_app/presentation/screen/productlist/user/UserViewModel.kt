package com.example.peya_ecommerce_app.presentation.screen.productlist.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.peya_ecommerce_app.data.local.UserPreferences
import com.example.peya_ecommerce_app.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val isLoggedIn: Flow<Boolean> = userPreferences.isLoggedIn

    fun logout(navController: NavController) {
        viewModelScope.launch {
            userPreferences.clearUser()

            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.ProductList.route) { inclusive = true }
            }
        }
    }
}