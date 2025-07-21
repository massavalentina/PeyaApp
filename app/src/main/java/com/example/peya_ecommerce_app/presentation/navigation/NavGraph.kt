package com.example.peya_ecommerce_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peya_ecommerce_app.presentation.screen.productlist.cart.CartScreen
import com.example.peya_ecommerce_app.presentation.screen.productlist.login.LoginScreen
import com.example.peya_ecommerce_app.presentation.screen.productlist.ProductListScreen
import com.example.peya_ecommerce_app.presentation.screen.productlist.register.RegisterScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.peya_ecommerce_app.presentation.components.BottomNavBar
import androidx.compose.material3.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.peya_ecommerce_app.presentation.screen.productlist.orders.UserOrdersScreen
import com.example.peya_ecommerce_app.presentation.screen.productlist.register.EmailVerifyScreen
import com.example.peya_ecommerce_app.presentation.screen.productlist.user.UserProfileScreen
import com.example.peya_ecommerce_app.presentation.screen.productlist.user.UserViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ProductList : Screen("products")
    object Cart : Screen("cart")
    object UserProfile : Screen("user_profile")
    object EmailVerify : Screen("email_verify")
    object UserOrders : Screen("user_orders")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    val userViewModel: UserViewModel = hiltViewModel()
    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.Login.route,
                    Screen.Register.route,
                    Screen.ProductList.route,
                    Screen.Cart.route,
                    Screen.UserProfile.route,
                    Screen.UserOrders.route
                )
            ) {
                BottomNavBar(navController = navController, userViewModel = userViewModel)
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
            composable(Screen.EmailVerify.route) { EmailVerifyScreen(navController)}
            composable(Screen.UserProfile.route) { UserProfileScreen(navController = navController) }
            composable(Screen.UserOrders.route) { UserOrdersScreen(navController = navController)}

        }
    }
}
