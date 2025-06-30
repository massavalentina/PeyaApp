package com.example.peya_ecommerce_app.model

data class Product(//agregar id
    val nombre: String,
    val precio: Double,
    val imagenUrl: String,
    val categoria: String
)
