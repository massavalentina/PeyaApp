package com.example.peya_ecommerce_app.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.peya_ecommerce_app.presentation.navigation.Screen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Login.route, "Login", Icons.Default.Person),
    BottomNavItem(Screen.Register.route, "Register", Icons.Default.PersonAdd),
    BottomNavItem(Screen.ProductList.route, "Products", Icons.Default.List),
    BottomNavItem(Screen.Cart.route, "Cart", Icons.Default.ShoppingCart)
)
