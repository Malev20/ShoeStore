package com.example.shoestore.ui.screens
import androidx.compose.foundation.clickable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoestore.R
import com.example.shoestore.data.model.Category
import com.example.shoestore.data.model.Product
import com.example.shoestore.ui.components.ProductCard
import com.example.shoestore.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onFavoriteClick: (Product) -> Unit,
    onAllClick: () -> Unit = {}          // для перехода на Home по All
) {
    // список категорий сверху (как на Home)
    val categories = listOf(
        Category("All", isSelected = false),
        Category("Outdoor", isSelected = false),
        Category("Tennis", isSelected = false),
        Category("Running", isSelected = false),
    )

    var selectedCategory by remember { mutableStateOf(categoryName) }

    // 16 одинаковых товаров для примера
    val categoryProducts = List(16) { index ->
        Product(
            id = (index + 1).toString(),
            name = "Nike Air Max",
            price = "P752.00",
            originalPrice = "P850.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoryName,
                        style = AppTypography.headingRegular32.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF7F7F9),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF7F7F9))
        ) {
            // Блок категорий (чипсы)
            CategoryChipsRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { name ->
                    selectedCategory = name
                    if (name == "All") {
                        onAllClick()       // возвращаемся на Home
                    }
                    // если нужно — можно потом менять список товаров по категории
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Сетка из 16 карточек. LazyVerticalGrid уже скроллится по вертикали.
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp, top = 8.dp)
            ) {
                items(categoryProducts) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = { onProductClick(product) },
                        onFavoriteClick = { onFavoriteClick(product) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryChipsRow(
    categories: List<Category>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            CategoryChipSmall(
                category = category.name,
                isSelected = selectedCategory == category.name,
                onClick = { onCategorySelected(category.name) }
            )
        }
    }
}

@Composable
private fun CategoryChipSmall(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else Color.White
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            style = AppTypography.bodyMedium16.copy(
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Black
            )
        )
    }
}
