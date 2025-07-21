package com.example.peya_ecommerce_app.presentation.screen.productlist.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peya_ecommerce_app.data.repository.OrderRepository
import com.example.peya_ecommerce_app.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserOrdersUiState {
    object Loading : UserOrdersUiState()
    data class Success(val orders: List<Order>) : UserOrdersUiState()
    data class Error(val message: String) : UserOrdersUiState()
}

class UserOrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserOrdersUiState>(UserOrdersUiState.Loading)
    val uiState: StateFlow<UserOrdersUiState> = _uiState

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            _uiState.value = UserOrdersUiState.Loading
            try {
                val orders = orderRepository.getOrders()
                _uiState.value = UserOrdersUiState.Success(orders)
            } catch (e: Exception) {
                _uiState.value = UserOrdersUiState.Error(e.message ?: "Error al cargar las órdenes.")
            }
        }
    }
}