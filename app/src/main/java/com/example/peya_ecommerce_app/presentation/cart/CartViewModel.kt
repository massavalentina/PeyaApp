package com.example.peya_ecommerce_app.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peya_ecommerce_app.data.local.UserPreferences
import com.example.peya_ecommerce_app.data.repository.CartRepository
import com.example.peya_ecommerce_app.data.repository.OrderRepository
import com.example.peya_ecommerce_app.model.CartItem
import com.example.peya_ecommerce_app.model.Product

import kotlinx.coroutines.flow.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import com.example.peya_ecommerce_app.model.Order
import com.example.peya_ecommerce_app.model.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userPreferences: UserPreferences,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalPrice: StateFlow<Double> = cartItems
        .map { items -> items.sumOf { it.product.precio * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val existingItem = cartItems.value.find { it.product == product }
            val newQuantity = (existingItem?.quantity ?: 0) + 1
            cartRepository.addCartItem(product, newQuantity)
        }
    }

    fun increment(product: Product) {
        addToCart(product)
    }

    fun decrement(product: Product) {
        viewModelScope.launch {
            val existingItem = cartItems.value.find { it.product == product }
            if (existingItem != null) {
                val newQuantity = existingItem.quantity - 1
                if (newQuantity > 0) {
                    cartRepository.addCartItem(product, newQuantity)
                } else {
                    cartRepository.removeCartItem(product)
                }
            }
        }
    }

    fun removeItem(product: Product) {
        viewModelScope.launch {
            cartRepository.removeCartItem(product)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    fun confirmOrder(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val userId = userPreferences.userId.first()
                if (userId == null) {
                    onError("Usuario no logueado")
                    return@launch
                }

                val order = createOrder(userId)
                orderRepository.createOrder(order)
                clearCart()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al confirmar la orden")
            }
        }
    }

    private fun createOrder(userId: String): Order {
        val orderItems = cartItems.value.map { cartItem ->
            OrderItem(
                name = cartItem.product.nombre,
                description = cartItem.product.descripcion ?: "",
                imageUrl = cartItem.product.imagenUrl,
                price = cartItem.product.precio,
                hasDrink = cartItem.product.hasDrink,
                quantity = cartItem.quantity
            )
        }
        return Order(
            orderId = "ORD-${System.currentTimeMillis()}",
            productIds = orderItems,
            total = totalPrice.value,
            timestamp = System.currentTimeMillis(),
            userId = userId
        )
    }
}