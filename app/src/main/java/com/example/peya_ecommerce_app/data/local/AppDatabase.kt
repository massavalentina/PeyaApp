package com.example.peya_ecommerce_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.peya_ecommerce_app.data.local.Cart.CartDao


@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}