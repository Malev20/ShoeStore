package com.example.shoestore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoestore.R
import com.example.shoestore.data.model.Category
import com.example.shoestore.data.model.Product
import com.example.shoestore.ui.components.ProductCard
import com.example.shoestore.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    var selected by rememberSaveable { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf(
        Category("All", isSelected = true),
        Category("Outdoor", isSelected = false),
        Category("Tennis", isSelected = false),
        Category("Running", isSelected = false),
        Category("Casual", isSelected = false)
    )

    val popularProducts = listOf(
        Product(
            id = "1",
            name = "Nike Air Max",
            price = "P752.00",
            originalPrice = "P850.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "2",
            name = "Nike Air Force 1",
            price = "P820.00",
            originalPrice = "P900.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "3",
            name = "Adidas Ultraboost",
            price = "P680.00",
            originalPrice = "P750.00",
            category = "NEW",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "4",
            name = "Puma RS-X",
            price = "P520.00",
            originalPrice = "P600.00",
            category = "TRENDING",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        )
    )

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vector_1789),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        IconButton(onClick = { selected = 0 }) {
                            Icon(
                                painter = painterResource(id = R.drawable.home),
                                contentDescription = "Home",
                                tint = if (selected == 0) MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = { selected = 1 }) {
                            Icon(
                                painter = painterResource(id = R.drawable.favorite),
                                contentDescription = "Favorites",
                                tint = if (selected == 1) MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .offset(y = (-20).dp)
                            .size(56.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingActionButton(
                            onClick = { onCartClick() },
                            modifier = Modifier.size(56.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shape = CircleShape
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.bag_2),
                                contentDescription = "Cart",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Row {
                        IconButton(onClick = { selected = 2 }) {
                            Icon(
                                painter = painterResource(id = R.drawable.orders),
                                contentDescription = "Notification",
                                tint = if (selected == 2) MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = { selected = 3 }) {
                            Icon(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Profile",
                                tint = if (selected == 3) MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF7F7F9))
        ) {
            if (selected == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.home),
                        style = AppTypography.headingRegular32,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                        ) {
                            OutlinedTextField(
                                value = "",
                                onValueChange = { onSearchClick() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.search),
                                        style = AppTypography.bodyRegular14
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = Color.Gray
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.LightGray,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable { onSettingsClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sliders),
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (selected) {
                    0 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            item {
                                CategorySection(
                                    categories = categories,
                                    selectedCategory = selectedCategory,
                                    onCategorySelected = { category ->
                                        selectedCategory = category
                                    },
                                    onCategoryClick = { name ->
                                        if (name != "All") {
                                            onCategoryClick(name)
                                        }
                                    }
                                )
                            }

                            item {
                                PopularSection(
                                    products = popularProducts,
                                    onProductClick = onProductClick,
                                    onFavoriteClick = { _ -> }
                                )
                            }

                            item {
                                PromotionsSection()
                            }
                        }
                    }

                    1 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.favourite),
                                style = AppTypography.headingRegular32
                            )
                        }
                    }

                    2 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.notifications),
                                style = AppTypography.headingRegular32
                            )
                        }
                    }

                    3 -> {
                        ProfileScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun CategorySection(
    categories: List<Category>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onCategoryClick: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.categories),
            style = AppTypography.bodyMedium16.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryChip(
                    category = category.name,
                    isSelected = selectedCategory == category.name,
                    onClick = {
                        onCategorySelected(category.name)
                        onCategoryClick(category.name)
                    }
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(108.dp)
            .height(40.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else Color.White
            )
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = if (isSelected) Color.Transparent
                else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            style = AppTypography.bodyMedium16.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 14.sp,
                color = if (isSelected) Color.White else Color.Black
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PopularSection(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onFavoriteClick: (Product) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.popular),
                style = AppTypography.bodyMedium16.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = stringResource(R.string.all),
                style = AppTypography.bodyRegular12.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onProductClick = { onProductClick(product) },
                    onFavoriteClick = { onFavoriteClick(product) }
                )
            }
        }
    }
}

@Composable
private fun PromotionsSection() {
    Column {
        Text(
            text = stringResource(R.string.sales),
            style = AppTypography.bodyMedium16.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3.2f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color.Black.copy(alpha = 0.1f)
                ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sale),
                contentDescription = "Sale",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onProductClick = {},
        onCartClick = {},
        onSearchClick = {},
        onSettingsClick = {},
        onCategoryClick = {}
    )
}
