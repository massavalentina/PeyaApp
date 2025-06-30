package com.example.peya_ecommerce_app.presentation.productlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peya_ecommerce_app.presentation.components.ProductCard
import com.example.peya_ecommerce_app.presentation.components.SearchFilterBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*


@Composable
fun ProductListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = viewModel()
) {
    val productList by viewModel.filteredProducts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val category by viewModel.selectedCategory.collectAsState()
    val categories = listOf("Todos", "Comida", "Bebidaa", "Postre")


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 4.dp
            ) {
                SearchFilterBar(
                    query = searchQuery,
                    onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    onFilterClick = { viewModel.onCategorySelected(it) },
                    selectedCategory = category,
                    availableCategories = categories
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productList) { product ->
                ProductCard(product)
            }
        }
    }
}


