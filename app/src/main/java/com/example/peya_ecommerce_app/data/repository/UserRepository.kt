package com.example.peya_ecommerce_app.data.repository

import com.example.peya_ecommerce_app.data.remote.AuthResponse
import com.example.peya_ecommerce_app.data.remote.AuthService
import com.example.peya_ecommerce_app.data.remote.LoginRequest
import com.example.peya_ecommerce_app.data.remote.RegisterRequest
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authService: AuthService
) {

    suspend fun registerUser(fullName: String, email: String, password: String): Result<Unit> {
        return try {
            val request = RegisterRequest(fullName, email, password)
            val response = authService.register(request)

            if (response.success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun authenticateUser(email: String, password: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = authService.login(request)

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

