package com.example.peya_ecommerce_app.model

data class Product(
    val id: String,
    val nombre: String,
    val precio: Double,
    val imagenUrl: String,
    val categoria: String? = null,
    val descripcion: String? = null,
    val hasDrink: Boolean

)
