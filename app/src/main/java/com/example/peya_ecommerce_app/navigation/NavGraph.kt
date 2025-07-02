package com.example.peya_ecommerce_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peya_ecommerce_app.presentation.cart.CartScreen
import com.example.peya_ecommerce_app.presentation.login.LoginScreen
import com.example.peya_ecommerce_app.presentation.productlist.ProductListScreen
import com.example.peya_ecommerce_app.presentation.register.RegisterScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.peya_ecommerce_app.presentation.components.BottomNavBar
import com.example.peya_ecommerce_app.presentation.components.bottomNavItems
import androidx.compose.material3.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ProductList : Screen("product_list")
    object Cart : Screen("cart")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                BottomNavBar(navController, bottomNavItems)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.Register.route) { RegisterScreen(navController) }
            composable(Screen.ProductList.route) { ProductListScreen(navController) }
            composable(Screen.Cart.route) { CartScreen(navController) }
        }
    }
}
