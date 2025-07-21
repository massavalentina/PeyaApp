package com.example.peya_ecommerce_app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.peya_ecommerce_app.model.Product

import androidx.compose.material3.ElevatedCard

@Composable
fun ProductCard(
    product: Product,
    quantityInCart: Int,
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (Product) -> Unit
) {
    ElevatedCard( // Utilizamos ElevatedCard de Material 3
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.elevatedCardColors()
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Padding interno de la tarjeta
        ) {
            Row {
                AsyncImage(
                    model = product.imagenUrl,
                    contentDescription = product.nombre,
                    modifier = Modifier
                        .size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = product.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Precio: $${product.precio}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (quantityInCart > 0) {
                        Text(
                            text = "En carrito: $quantityInCart",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onAddToCart(product) }) {
                    Text("Agregar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (quantityInCart > 0) {
                    Button(onClick = { onRemoveFromCart(product) }) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}





