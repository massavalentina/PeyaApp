package com.example.peya_ecommerce_app.data.remote

import retrofit2.http.GET

interface ProductService {
    @GET("foods")
    suspend fun getProducts(): List<ProductDto>
}