package com.example.peya_ecommerce_app.data.local.Cart

import androidx.room.*
import com.example.peya_ecommerce_app.data.local.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity): Long

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity): Int

    @Query("DELETE FROM cart_items")
    suspend fun clearCart(): Int
}