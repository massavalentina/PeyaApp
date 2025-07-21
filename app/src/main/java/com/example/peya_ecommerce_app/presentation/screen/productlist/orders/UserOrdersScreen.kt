package com.example.peya_ecommerce_app.presentation.screen.productlist.orders

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import android.text.format.DateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peya_ecommerce_app.model.Order
import com.example.peya_ecommerce_app.model.OrderItem

@Composable
fun UserOrdersScreen(
    navController: NavController,
    viewModel: UserOrdersViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Historial de Órdenes") })
    }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState.value) {
                is UserOrdersUiState.Loading -> LoadingView()
                is UserOrdersUiState.Success -> OrdersList(state.orders)
                is UserOrdersUiState.Error -> ErrorView(state.message) {
                    viewModel.loadOrders()
                }
            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(orders) { order ->
            OrderCard(order)
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Orden ID: ${order.orderId}", style = MaterialTheme.typography.h6)
            Text(text = "Total: $${order.total}", style = MaterialTheme.typography.body1)
            Text(text = "Fecha: ${formatDate(order.timestamp)}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Productos:", style = MaterialTheme.typography.subtitle1)
            order.productIds.forEach { product ->
                OrderItemCard(product)
            }
        }
    }
}

@Composable
fun OrderItemCard(orderItem: OrderItem) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = "Producto: ${orderItem.name}", style = MaterialTheme.typography.body1)
        Text(text = "Descripción: ${orderItem.description}", style = MaterialTheme.typography.body2)
        Text(text = "Cantidad: ${orderItem.quantity}", style = MaterialTheme.typography.body2)
        Text(text = "Precio Unitario: $${orderItem.price}", style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

fun formatDate(timestamp: Long): String {
    return DateFormat.format("dd/MM/yyyy", timestamp).toString()
}
