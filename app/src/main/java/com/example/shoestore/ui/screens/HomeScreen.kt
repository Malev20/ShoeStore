package com.example.shoestore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.R
import com.example.shoestore.data.model.Category
import com.example.shoestore.data.model.Product
import com.example.shoestore.ui.HomeViewModel
import com.example.shoestore.ui.components.ProductCard
import com.example.shoestore.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    // 0 = Home, 1 = Favourite, 2 = Notifications, 3 = Profile
    var selected by rememberSaveable { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf(
        Category("All", isSelected = true),
        Category("Outdoor"),
        Category("Tennis"),
        Category("Running"),
        Category("Casual")
    )

    val popularProducts = listOf(
        Product(
            id = "1",
            name = "Nike Air Max",
            price = "₽752.00",
            originalPrice = "₽850.00",
            category = "BEST SELLER",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "2",
            name = "Nike Air Force 1",
            price = "₽820.00",
            originalPrice = "₽900.00",
            category = "BEST SELLER",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "3",
            name = "Adidas Ultraboost",
            price = "₽680.00",
            originalPrice = "₽750.00",
            category = "NEW",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "4",
            name = "Adidas Ultraboost",
            price = "₽680.00",
            originalPrice = "₽750.00",
            category = "NEW",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "5",
            name = "Adidas Ultraboost",
            price = "₽680.00",
            originalPrice = "₽750.00",
            category = "NEW",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "6",
            name = "Adidas Ultraboost",
            price = "₽680.00",
            originalPrice = "₽750.00",
            category = "NEW",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "7",
            name = "Puma RS-X",
            price = "₽520.00",
            originalPrice = "₽600.00",
            category = "TRENDING",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        )
    )

    LaunchedEffect(Unit) {
        viewModel.setProducts(popularProducts)
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                selected = selected,
                onSelectedChange = { selected = it },
                onCartClick = onCartClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF7F7F9))
        ) {
            if (selected == 0) {
                HomeTopBar(onSearchClick = onSearchClick, onSettingsClick = onSettingsClick)
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (selected) {
                    0 -> HomeContent(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it },
                        onCategoryClick = onCategoryClick,
                        products = popularProducts,
                        onProductClick = onProductClick,
                        onFavoriteClick = { p -> viewModel.toggleFavorite(p) },
                        isFavorite = { p -> viewModel.isFavorite(p) }
                    )

                    1 -> FavoriteScreen(
                        products = viewModel.favoriteProducts(),
                        isFavorite = { p -> viewModel.isFavorite(p) },
                        onProductClick = onProductClick,
                        onFavoriteClick = { p -> viewModel.toggleFavorite(p) }
                    )

                    2 -> CenterTextScreen("Notifications")
                    3 -> {
                        ProfileScreen()
                    }

                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    selected: Int,
    onSelectedChange: (Int) -> Unit,
    onCartClick: () -> Unit
) {
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
                IconButton(onClick = { onSelectedChange(0) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home",
                        tint = if (selected == 0) MaterialTheme.colorScheme.primary else Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { onSelectedChange(1) }) {
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
                    onClick = onCartClick,
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
                IconButton(onClick = { onSelectedChange(2) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.orders),
                        contentDescription = "Notification",
                        tint = if (selected == 2) MaterialTheme.colorScheme.primary else Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { onSelectedChange(3) }) {
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

@Composable
private fun HomeTopBar(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Home",
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
                            text = "Search",
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

@Composable
private fun HomeContent(
    categories: List<Category>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onFavoriteClick: (Product) -> Unit,
    isFavorite: (Product) -> Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            CategorySection(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
                onCategoryClick = onCategoryClick
            )
        }

        item {
            PopularSection(
                products = products,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteClick,
                isFavorite = isFavorite
            )
        }

        item {
            PromotionsSection()
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
            text = "Categories",
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
    onFavoriteClick: (Product) -> Unit,
    isFavorite: (Product) -> Boolean
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular",
                style = AppTypography.bodyMedium16.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = "All",
                style = AppTypography.bodyRegular12.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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

@Composable
private fun PromotionsSection() {
    Column {
        Text(
            text = "Sales",
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

@Composable
private fun CenterTextScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = AppTypography.headingRegular32
        )
    }
}
