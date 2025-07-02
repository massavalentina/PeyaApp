package com.example.peya_ecommerce_app.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peya_ecommerce_app.model.CartItem
import com.example.peya_ecommerce_app.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val totalPrice: StateFlow<Double> = cartItems
        .map { list -> list.sumOf { it.product.precio * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun addToCart(product: Product) {
        viewModelScope.launch {
            delay(300) // Simula acceso a BD
            val current = _cartItems.value.toMutableList()
            val index = current.indexOfFirst { it.product == product }
            if (index != -1) {
                val updatedItem = current[index].copy(quantity = current[index].quantity + 1)
                current[index] = updatedItem
            } else {
                current.add(CartItem(product))
            }
            _cartItems.value = current
        }
    }

    fun increment(product: Product) {
        viewModelScope.launch {
            delay(200)
            val current = _cartItems.value.toMutableList()
            val index = current.indexOfFirst { it.product == product }
            if (index != -1) {
                val item = current[index]
                current[index] = item.copy(quantity = item.quantity + 1)
                _cartItems.value = current
            }
        }
    }

    fun decrement(product: Product) {
        viewModelScope.launch {
            delay(200)
            val current = _cartItems.value.toMutableList()
            val index = current.indexOfFirst { it.product == product }
            if (index != -1) {
                val item = current[index]
                if (item.quantity > 1) {
                    current[index] = item.copy(quantity = item.quantity - 1)
                } else {
                    current.removeAt(index)
                }
                _cartItems.value = current
            }
        }
    }

    fun removeItem(product: Product) {
        viewModelScope.launch {
            delay(200)
            _cartItems.value = _cartItems.value.filter { it.product != product }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}


