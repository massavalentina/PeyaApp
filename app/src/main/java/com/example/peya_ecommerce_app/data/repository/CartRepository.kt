package com.example.peya_ecommerce_app.data.repository

import com.example.peya_ecommerce_app.data.local.Cart.CartDao
import com.example.peya_ecommerce_app.data.local.CartItemEntity
import com.example.peya_ecommerce_app.model.CartItem
import com.example.peya_ecommerce_app.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    val cartItems: Flow<List<CartItem>> = cartDao.getAllCartItems().map { entities ->
        entities.map { entity ->
            CartItem(
                product = Product(
                    id = entity.productId,
                    nombre = entity.productName,
                    precio = entity.productPrice,
                    imagenUrl = entity.productImageUrl,
                    hasDrink = entity.productHasDrink
                ),
                quantity = entity.quantity
            )
        }
    }

    suspend fun addCartItem(product: Product, quantity: Int) {
        val cartItemEntity = CartItemEntity(
            productId = product.id,
            productName = product.nombre,
            productPrice = product.precio,
            productImageUrl = product.imagenUrl,
            productHasDrink = product.hasDrink,
            quantity = quantity
        )
        cartDao.insertCartItem(cartItemEntity)
    }

    suspend fun removeCartItem(product: Product) {
        val cartItemEntity = CartItemEntity(
            productId = product.id,
            productName = product.nombre,
            productPrice = product.precio,
            productImageUrl = product.imagenUrl,
            productHasDrink = product.hasDrink,
            quantity = 0
        )
        cartDao.deleteCartItem(cartItemEntity)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}