package com.example.peya_ecommerce_app.presentation.screen.productlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peya_ecommerce_app.presentation.components.ProductCard
import com.example.peya_ecommerce_app.presentation.components.SearchFilterBar
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.peya_ecommerce_app.presentation.screen.productlist.cart.CartViewModel


@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val productList by viewModel.filteredProducts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val category by viewModel.selectedCategory.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    val categories = listOf("Todos", "Con Bebida", "Sin Bebida")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Productos") },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Ir al carrito")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchFilterBar(
                query = searchQuery,
                onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                onFilterClick = { viewModel.onCategorySelected(it) },
                selectedCategory = category,
                availableCategories = categories
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(productList) { product ->
                    val quantityInCart = cartItems.find { it.product.id == product.id }?.quantity ?: 0

                    ProductCard(
                        product = product,
                        quantityInCart = quantityInCart,
                        onAddToCart = { cartViewModel.addToCart(it) },
                        onRemoveFromCart = { cartViewModel.removeItem(it) }
                    )
                }
            }
        }
    }
}

