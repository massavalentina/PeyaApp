package com.example.peya_ecommerce_app.presentation.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.peya_ecommerce_app.presentation.navigation.Screen
import com.example.peya_ecommerce_app.presentation.screen.productlist.user.UserViewModel
import androidx.compose.runtime.getValue

@Composable
fun BottomNavBar(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel() // Detectar si el usuario está logueado
) {
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState(initial = false)

    Log.d("BottomBarState", "isLoggedIn: $isLoggedIn")
    // Lista dinámica de ítems del BottomNav
    val bottomNavItems = if (isLoggedIn) {
        listOf(
            BottomNavItem(Screen.UserProfile.route, "Cuenta", Icons.Default.Person), // Sección de Cuenta
            BottomNavItem(Screen.ProductList.route, "Productos", Icons.Default.List),
            BottomNavItem(Screen.Cart.route, "Carrito", Icons.Default.ShoppingCart)
        )
    } else {
        listOf(
            BottomNavItem(Screen.Login.route, "Login", Icons.Default.Person), // Opción de Login
            BottomNavItem(Screen.Register.route, "Registrarse", Icons.Default.PersonAdd),
            BottomNavItem(Screen.ProductList.route, "Productos", Icons.Default.List),
            BottomNavItem(Screen.Cart.route, "Carrito", Icons.Default.ShoppingCart)
        )
    }

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
