package com.example.shoestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoestore.ui.screens.RegisterAccountScreen
import com.example.shoestore.ui.theme.ShoeStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoeStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoeStoreApp()
                }
            }
        }
    }
}

@Composable
fun ShoeStoreApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "register"
    ) {
        composable("register") {
            RegisterAccountScreen(
                onNavigateToSignIn = {
                    // Переход на экран входа
                    navController.navigate("signin")
                }
            )
        }

        composable("signin") {
            // Заглушка для экрана входа
            androidx.compose.material3.Text(
                text = "Экран входа (будет реализован в Day-2)",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// Для предпросмотра в Android Studio
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ShoeStoreTheme {
        RegisterAccountScreen(
            onNavigateToSignIn = {}
        )
    }
}