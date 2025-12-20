// com/example/shoestore/ui/screens/FavoriteScreen.kt
package com.example.shoestore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shoestore.R
import com.example.shoestore.data.model.Product
import com.example.shoestore.ui.components.ProductCard
import com.example.shoestore.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    products: List<Product>,
    isFavorite: (Product) -> Boolean,
    onProductClick: (Product) -> Unit,
    onFavoriteClick: (Product) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.favourite),
                        style = AppTypography.headingRegular32
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF7F7F9),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F9)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No featured products")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F9))
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp, top = 8.dp)
            ) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = isFavorite(product),
                        onProductClick = { onProductClick(product) },
                        onFavoriteClick = { onFavoriteClick(product) }
                    )
                }
            }
        }
    }
}
