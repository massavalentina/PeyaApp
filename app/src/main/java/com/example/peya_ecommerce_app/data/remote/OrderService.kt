package com.example.peya_ecommerce_app.data.remote

import com.example.peya_ecommerce_app.model.Order
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {

    @GET("orders")
    suspend fun getOrders(): List<Order>

    @POST("orders")
    suspend fun createOrder(@Body order: Order): Order
}