package com.example.peya_ecommerce_app.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
}

data class RegisterRequest(
    val fullName: String,
    val email: String,
    @SerializedName("encryptedPassword") val password: String
)

data class LoginRequest(
    val email: String,
    @SerializedName("encryptedPassword") val password: String
)

data class AuthResponse(
    val message: String,
    val success: Boolean,
    @SerializedName("_id") val userId: String,
    val email: String
    )