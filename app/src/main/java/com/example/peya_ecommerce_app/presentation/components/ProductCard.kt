package com.example.peya_ecommerce_app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.peya_ecommerce_app.model.Product
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

@Composable
fun ProductCard(
    product: Product,
    quantityInCart: Int,
    onAddToCart: (Product) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = product.imagenUrl,
                    contentDescription = product.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = product.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$${product.precio}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (product.hasDrink) {
                    Icon(
                        imageVector = Icons.Default.LocalDrink,
                        contentDescription = "Incluye bebida",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onAddToCart(product) }) {
                Text("Agregar al carrito (${quantityInCart})")
            }
        }
    }
}






