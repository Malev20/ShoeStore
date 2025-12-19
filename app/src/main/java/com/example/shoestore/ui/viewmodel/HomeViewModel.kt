// com/example/shoestore/ui/HomeViewModel.kt
package com.example.shoestore.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shoestore.data.model.Product

class HomeViewModel : ViewModel() {

    var allProducts: List<Product> = emptyList()

    private val _favoriteIds = mutableStateListOf<String>()
    val favoriteIds: List<String> get() = _favoriteIds

    fun setProducts(list: List<Product>) {
        allProducts = list
    }

    fun isFavorite(product: Product): Boolean =
        _favoriteIds.contains(product.id)

    fun toggleFavorite(product: Product) {
        if (_favoriteIds.contains(product.id)) {
            _favoriteIds.remove(product.id)
        } else {
            _favoriteIds.add(product.id)
        }
    }

    fun favoriteProducts(): List<Product> =
        allProducts.filter { _favoriteIds.contains(it.id) }
}
