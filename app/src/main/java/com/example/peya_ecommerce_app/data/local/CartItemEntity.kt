package com.example.peya_ecommerce_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val productName: String,
    val productPrice: Double,
    val productImageUrl: String,
    val productHasDrink: Boolean,
    val quantity: Int
)