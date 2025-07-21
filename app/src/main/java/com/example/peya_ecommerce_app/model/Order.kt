package com.example.peya_ecommerce_app.model

data class Order(
    val orderId: String,
    val productIds: List<OrderItem>,
    val total: Double,
    val timestamp: Long,
    val userId : String
)

data class OrderItem(
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    val quantity: Int
)
