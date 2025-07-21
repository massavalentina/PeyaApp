package com.example.peya_ecommerce_app.data.repository

import com.example.peya_ecommerce_app.data.local.Cart.CartDao
import com.example.peya_ecommerce_app.data.local.CartItemEntity
import com.example.peya_ecommerce_app.model.CartItem
import com.example.peya_ecommerce_app.model.Product
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CartRepositoryTest {

    private lateinit var cartDao: CartDao
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartDao = mockk()
        cartRepository = CartRepository(cartDao)
    }

    @Test
    fun `addCartItem should insert item into cart`() = runTest {
        val product = Product(
            id = "1",
            nombre = "Producto 1",
            precio = 100.0,
            imagenUrl = "https://imagen.com/1",
            hasDrink = true
        )
        val quantity = 2

        val expectedEntity = CartItemEntity(
            productId = product.id,
            productName = product.nombre,
            productPrice = product.precio,
            productImageUrl = product.imagenUrl,
            productHasDrink = product.hasDrink,
            quantity = quantity
        )

        coEvery { cartDao.insertCartItem(expectedEntity) } returns 1L

        cartRepository.addCartItem(product, quantity)

        coVerify(exactly = 1) { cartDao.insertCartItem(expectedEntity) }
    }

    @Test
    fun `removeCartItem should delete item from cart`() = runTest {
        val product = Product(
            id = "1",
            nombre = "Producto 1",
            precio = 100.0,
            imagenUrl = "https://imagen.com/1",
            hasDrink = true
        )

        val expectedEntity = CartItemEntity(
            productId = product.id,
            productName = product.nombre,
            productPrice = product.precio,
            productImageUrl = product.imagenUrl,
            productHasDrink = product.hasDrink,
            quantity = 0
        )

        coEvery { cartDao.deleteCartItem(expectedEntity) } returns 1

        cartRepository.removeCartItem(product)

        coVerify(exactly = 1) { cartDao.deleteCartItem(expectedEntity) }
    }

    @Test
    fun `clearCart should clear all items from cart`() = runTest {
        coEvery { cartDao.clearCart() } returns 1

        cartRepository.clearCart()

        coVerify(exactly = 1) { cartDao.clearCart() }
    }

    @Test
    fun `cartItems should transform CartItemEntity to CartItem`() = runTest {
        val cartItemEntities = listOf(
            CartItemEntity(
                productId = "1",
                productName = "Producto 1",
                productPrice = 100.0,
                productImageUrl = "https://imagen.com/1",
                productHasDrink = true,
                quantity = 2
            ),
            CartItemEntity(
                productId = "2",
                productName = "Producto 2",
                productPrice = 200.0,
                productImageUrl = "https://imagen.com/2",
                productHasDrink = false,
                quantity = 1
            )
        )

        val expectedCartItems = listOf(
            CartItem(
                product = Product(
                    id = "1",
                    nombre = "Producto 1",
                    precio = 100.0,
                    imagenUrl = "https://imagen.com/1",
                    hasDrink = true
                ),
                quantity = 2
            ),
            CartItem(
                product = Product(
                    id = "2",
                    nombre = "Producto 2",
                    precio = 200.0,
                    imagenUrl = "https://imagen.com/2",
                    hasDrink = false
                ),
                quantity = 1
            )
        )

        every { cartDao.getAllCartItems() } returns flowOf(cartItemEntities)

        val result = cartRepository.cartItems.first()

        assertEquals(expectedCartItems, result)
    }
}