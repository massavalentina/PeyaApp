package com.example.peya_ecommerce_app.data.repository

import com.example.peya_ecommerce_app.data.remote.OrderService
import com.example.peya_ecommerce_app.model.Order
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderService: OrderService
) {

    suspend fun createOrder(order: Order): Order {
        return orderService.createOrder(order)
    }
}