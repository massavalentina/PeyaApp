package com.example.peya_ecommerce_app.data.repository

import com.example.peya_ecommerce_app.data.remote.ProductService
import com.example.peya_ecommerce_app.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) {

    suspend fun getProducts(): List<Product> {
        // Llama al servicio y convierte los DTOs al modelo interno Product
        return productService.getProducts().map { dto ->
            Product(
                id = dto._id,                     // Mapeo desde _id
                nombre = dto.name,                // Mapeo desde name
                precio = dto.price,               // Mapeo directo
                imagenUrl = dto.imageUrl,         // Mapeo directo
                descripcion = dto.description,     // Mapeo directo
                hasDrink = dto.hasDrink
                // Puedes agregar más manipulación aquí según tus necesidades
            )
        }
    }
}