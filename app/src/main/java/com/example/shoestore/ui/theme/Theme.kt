package com.example.shoestore.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Определяем цвета, которые используются в вашем дизайне
private val AccentColor = Color(0xFF48B2E7)
private val BackgroundColor = Color(0xFFF5F5F5)
private val TextColor = Color(0xFF000000)
private val SubTextColor = Color(0xFF666666)
private val BlockColor = Color(0xFFFFFFFF)
private val InactiveButtonColor = Color(0xFF2B6B8B)

private val LightColorScheme = lightColorScheme(
    primary = AccentColor,           // Основной акцентный цвет
    secondary = InactiveButtonColor, // Вторичный цвет
    tertiary = Color(0xFFE8F5FF),    // Дополнительный светлый
    background = BackgroundColor,    // Фон экрана
    surface = BlockColor,            // Фон блоков
    onPrimary = Color.White,         // Текст на основном цвете
    onSecondary = Color.White,       // Текст на вторичном цвете
    onBackground = TextColor,        // Основной текст
    onSurface = TextColor,           // Текст на поверхности
    surfaceVariant = SubTextColor    // Вариант для второстепенного текста
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF48B2E7),
    secondary = Color(0xFF2B6B8B),
    tertiary = Color(0xFF1A3A4D),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF9E9E9E)
)

@Composable
fun ShoeStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Отключил dynamicColor, т.к. у вас специфичные цвета
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}