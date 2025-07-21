package com.example.peya_ecommerce_app.presentation.screen.productlist.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.alpha

@Composable
fun EmptyCartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Carrito vacío",
            tint = Color.Gray,
            modifier = Modifier
                .size(100.dp)
                .alpha(0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Tu carrito está vacío", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("productos") }) {
            Text("Ver productos")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEmptyCart() {
    val navController = rememberNavController()
    EmptyCartScreen(navController = navController)
}
