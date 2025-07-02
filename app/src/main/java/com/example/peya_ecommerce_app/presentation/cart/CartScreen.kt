package com.example.peya_ecommerce_app.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.peya_ecommerce_app.model.CartItem
import com.example.peya_ecommerce_app.model.Product

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total by cartViewModel.totalPrice.collectAsState()

    if (cartItems.isEmpty()) {
        EmptyCartScreen(navController)
        return
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Carrito", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(cartItems) { item ->
                CartItemRow(item, cartViewModel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Total: $${"%.2f".format(total)}", style = MaterialTheme.typography.h6)
    }
}

@Composable
fun CartItemRow(item: CartItem, cartViewModel: CartViewModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        AsyncImage(
            model = item.product.imagenUrl,
            contentDescription = item.product.nombre,
            modifier = Modifier.size(80.dp)
        )

        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
            Text(item.product.nombre)
            Text("Precio: $${item.product.precio}")
            Text("Subtotal: $${"%.2f".format(item.product.precio * item.quantity)}")
        }

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            IconButton(onClick = { cartViewModel.decrement(item.product) }) {
                Icon(Icons.Default.Remove, contentDescription = "Menos")
            }
            Text("${item.quantity}")
            IconButton(onClick = { cartViewModel.increment(item.product) }) {
                Icon(Icons.Default.Add, contentDescription = "Más")
            }
        }

        IconButton(onClick = { cartViewModel.removeItem(item.product) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}


