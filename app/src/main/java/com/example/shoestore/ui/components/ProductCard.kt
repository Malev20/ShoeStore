// com/example/shoestore/ui/components/ProductCard.kt
package com.example.shoestore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoestore.R
import com.example.shoestore.data.model.Product
import com.example.shoestore.ui.theme.AppTypography

@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onProductClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { onProductClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {

            // ===== КАРТИНКА =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                if (product.imageResId != null) {
                    Image(
                        painter = painterResource(product.imageResId),
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                }

                // ===== СЕРДЕЧКО =====
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Избранное",
                        tint = if (isFavorite) Color.Red else Color.Black
                    )
                }
            }

            // ===== НИЖНЯЯ ЧАСТЬ =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                Column {
                    Text(
                        text = product.category,
                        style = AppTypography.bodyRegular12,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = product.name,
                        style = AppTypography.bodyRegular16,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = product.price,
                        style = AppTypography.bodyRegular14
                    )
                }

                // ===== КНОПКА ЛИСТОЧЕК =====
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 6.dp, y = 6.dp)
                        .size(36.dp)
                        .background(
                            color = Color(0xFF4CB6E8),
                            shape = LeafButtonShape() // твоя форма
                        )
                        .clickable { /* TODO: add to cart */ },
                    contentAlignment = Alignment.Center
                ) {

                    if (isFavorite) {
                        // КОРЗИНА
                        Icon(
                            painter = painterResource(id = R.drawable.cart_na),
                            contentDescription = "В корзине",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    } else {
                        // ПЛЮС
                        Icon(
                            painter = painterResource(id = R.drawable.plisik_),
                            contentDescription = "Добавить",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    ProductCard(
        product = Product(
            id = "1",
            name = "Nike Air Max",
            price = "₽752.00",
            originalPrice = "₽850.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable
                .nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        isFavorite = false,
        onProductClick = {},
        onFavoriteClick = {}
    )
}
