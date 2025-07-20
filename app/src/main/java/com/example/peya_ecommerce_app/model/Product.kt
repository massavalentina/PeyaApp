package com.example.peya_ecommerce_app.model

data class Product(
    val id: String,             // ID mapeado desde _id de la API
    val nombre: String,         // Nombre del producto (adaptado desde name)
    val precio: Double,         // Precio
    val imagenUrl: String,      // URL de la imagen
    val categoria: String? = null,  // Categoría (puede ser localmente asignada)
    val descripcion: String? = null, // Descripción del producto
    val hasDrink: Boolean

)
