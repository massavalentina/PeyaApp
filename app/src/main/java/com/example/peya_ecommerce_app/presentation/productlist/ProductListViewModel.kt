package com.example.peya_ecommerce_app.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.peya_ecommerce_app.data.ProductRepository
import com.example.peya_ecommerce_app.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*


class ProductListViewModel : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory

    val filteredProducts: StateFlow<List<Product>> = combine(
        _allProducts,
        _searchQuery,
        _selectedCategory
    ) { products, query, category ->
        products
            .filter { it.nombre.contains(query, ignoreCase = true) }
            .filter {
                category == "Todos" || it.categoria.equals(category, ignoreCase = true)
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        loadProducts()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun loadProducts() {
        viewModelScope.launch {
            delay(500)
            _allProducts.value = ProductRepository.getProducts()
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }
}
