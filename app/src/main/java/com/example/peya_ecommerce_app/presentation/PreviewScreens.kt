package com.example.peya_ecommerce_app.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.peya_ecommerce_app.presentation.components.BottomNavBar
import com.example.peya_ecommerce_app.presentation.components.bottomNavItems
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import com.example.peya_ecommerce_app.presentation.login.LoginScreen

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewWithNavBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavBar(navController, bottomNavItems)
        }
    ) { padding ->
        LoginScreen(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
