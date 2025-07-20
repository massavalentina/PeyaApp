package com.example.peya_ecommerce_app.data.remote

data class ProductDto(
    val _id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    val createdAt: String,
    val updatedAt: String
)