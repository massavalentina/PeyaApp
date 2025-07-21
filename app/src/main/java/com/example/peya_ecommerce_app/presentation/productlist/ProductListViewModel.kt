package com.example.peya_ecommerce_app.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peya_ecommerce_app.data.repository.ProductRepository
import com.example.peya_ecommerce_app.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory

    // Productos filtrados según el texto de búsqueda y la categoría seleccionada
    val filteredProducts: StateFlow<List<Product>> = combine(
        _allProducts,
        _searchQuery,
        _selectedCategory
    ) { products, query, category ->
        products
            .filter { it.nombre.contains(query, ignoreCase = true) }
            .filter {
                when (category) {
                    "Todos" -> true                               // Mostrar todos los productos
                    "Con Bebida" -> it.hasDrink                  // Solo productos con bebida
                    "Sin Bebida" -> !it.hasDrink                 // Solo productos sin bebida
                    else -> true                                 // Default
                }
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _allProducts.value = productRepository.getProducts()
            } catch (e: Exception) {
                _allProducts.value = emptyList()
                e.printStackTrace()
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }
}
