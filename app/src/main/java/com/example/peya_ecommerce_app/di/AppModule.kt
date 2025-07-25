package com.example.peya_ecommerce.di

import android.content.Context
import androidx.room.Room
import com.example.peya_ecommerce_app.data.local.AppDatabase
import com.example.peya_ecommerce_app.data.local.Cart.CartDao
import com.example.peya_ecommerce_app.data.local.UserPreferences
import com.example.peya_ecommerce_app.data.remote.AuthService
import com.example.peya_ecommerce_app.data.remote.OrderService
import com.example.peya_ecommerce_app.data.remote.ProductService
import com.example.peya_ecommerce_app.data.repository.CartRepository
import com.example.peya_ecommerce_app.data.repository.OrderRepository
import com.example.peya_ecommerce_app.data.repository.ProductRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-demo-1qf1.onrender.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productService: ProductService
    ): ProductRepository {
        return ProductRepository(productService)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderService: OrderService): OrderRepository {
        return OrderRepository(orderService)
    }

    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepository(cartDao)
    }
}
