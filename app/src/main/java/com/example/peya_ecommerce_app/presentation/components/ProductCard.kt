package com.example.peya_ecommerce_app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.peya_ecommerce_app.model.Product
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

@Composable
fun ProductCard(product: Product) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = product.imagenUrl,
                contentDescription = product.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = null,
                error = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = product.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${product.precio}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

/*@Composable
@Preview(showBackground = true)
fun ProductCardPreview() {
    val demoProduct = Product(
        nombre = "Ejemplo de Producto",
        precio = 999.99,
        imagenUrl = "https://picsum.photos/200",
        categoria = "Demo"
    )

    ProductCard(product = demoProduct)
} */

