package com.example.peya_ecommerce_app.data.repository

import com.example.peya_ecommerce_app.data.remote.ProductService
import com.example.peya_ecommerce_app.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProductRepository @Inject constructor(
    private val productService: ProductService
) {

    suspend fun getProducts(): List<Product> {
        return productService.getProducts().map { dto ->
            Product(
                id = dto._id,
                nombre = dto.name,
                precio = dto.price,
                imagenUrl = dto.imageUrl,
                descripcion = dto.description,
                hasDrink = dto.hasDrink
            )
        }
    }
}