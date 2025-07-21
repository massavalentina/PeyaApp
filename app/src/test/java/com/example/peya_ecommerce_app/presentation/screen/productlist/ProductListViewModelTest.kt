package com.example.peya_ecommerce_app.presentation.screen.productlist

import com.example.peya_ecommerce_app.MainDispatcher
import com.example.peya_ecommerce_app.data.repository.ProductRepository
import com.example.peya_ecommerce_app.model.Product
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcher()

    private lateinit var productRepository: ProductRepository
    private lateinit var productListViewModel: ProductListViewModel

    @Before
    fun setUp() {
        productRepository = mockk()
        productListViewModel = ProductListViewModel(productRepository)
    }

    @After
    fun tearDown() {
        clearMocks(productRepository)
    }

    @Test
    fun `loadProducts should fetch products and update state`() = runTest {
        val mockProducts = listOf(
            Product(
                id = "1",
                nombre = "Producto 1",
                precio = 100.0,
                imagenUrl = "",
                hasDrink = true
            ),
            Product(
                id = "2",
                nombre = "Producto 2",
                precio = 200.0,
                imagenUrl = "",
                hasDrink = false
            )
        )
        coEvery { productRepository.getProducts() } returns mockProducts

        productListViewModel.loadProducts()

        val allProducts = productListViewModel.allProducts.first()
        assertEquals(mockProducts, allProducts)

        coVerify(exactly = 1) { productRepository.getProducts() }
    }


    @Test
    fun `onCategorySelected should filter products by category`() = runTest {
        val mockProducts = listOf(
            Product(
                id = "1",
                nombre = "Producto Con Bebida",
                precio = 150.0,
                imagenUrl = "",
                hasDrink = true
            ),
            Product(
                id = "2",
                nombre = "Producto Sin Bebida",
                precio = 100.0,
                imagenUrl = "",
                hasDrink = false
            )
        )
        coEvery { productRepository.getProducts() } returns mockProducts
        productListViewModel.loadProducts()

        productListViewModel.onCategorySelected("Con Bebida")

        val filteredProducts = productListViewModel.filteredProducts.first()
        assertEquals(1, filteredProducts.size)
        assertEquals("Producto Con Bebida", filteredProducts[0].nombre)

        productListViewModel.onCategorySelected("Sin Bebida")

        val filteredProductsSinBebida = productListViewModel.filteredProducts.first()
        assertEquals(1, filteredProductsSinBebida.size)
        assertEquals("Producto Sin Bebida", filteredProductsSinBebida[0].nombre)
    }

}