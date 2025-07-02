package com.example.peya_ecommerce_app.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
