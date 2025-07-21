package com.example.peya_ecommerce_app.presentation.screen.productlist.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.peya_ecommerce_app.model.CartItem
import com.example.peya_ecommerce_app.model.Product

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total by cartViewModel.totalPrice.collectAsState()

    if (cartItems.isEmpty()) {
        Text(
            text = "Tu carrito está vacío",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxSize().wrapContentSize()
        )
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(cartItems) { item ->
                CartItemRow(
                    cartItem = item,
                    onIncrement = { cartViewModel.increment(it) },
                    onDecrement = { cartViewModel.decrement(it) },
                    onRemove = { cartViewModel.removeItem(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Total: $${"%.2f".format(total)}", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                cartViewModel.confirmOrder(
                    onSuccess = {
                        navController.navigate("success")
                    },
                    onError = { error ->
                        if (error == "Usuario no logueado") {
                            navController.navigate("login")
                        } else {
                            println("Error: $error")
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar compra")
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncrement: (Product) -> Unit,
    onDecrement: (Product) -> Unit,
    onRemove: (Product) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        AsyncImage(
            model = cartItem.product.imagenUrl,
            contentDescription = cartItem.product.nombre,
            modifier = Modifier.size(80.dp)
        )

        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
            Text(cartItem.product.nombre)
            Text("Precio: $${cartItem.product.precio}")
            Text("Subtotal: $${"%.2f".format(cartItem.product.precio * cartItem.quantity)}")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onDecrement(cartItem.product) }) {
                Icon(Icons.Default.Remove, contentDescription = "Menos")
            }
            Text("${cartItem.quantity}")
            IconButton(onClick = { onIncrement(cartItem.product) }) {
                Icon(Icons.Default.Add, contentDescription = "Más")
            }
        }

        IconButton(onClick = { onRemove(cartItem.product) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}


