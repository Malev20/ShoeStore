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
import com.example.shoestore.ui.screens.CreateNewPasswordScreen
import com.example.shoestore.ui.screens.CreateNewPasswordScreen
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
                    CreateNewPasswordScreen(
                        onNavigateToNextScreen = {
                            println("Пароль сохранен, переход дальше")
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetNewPasswordPreview() {
    ShoeStoreTheme {
        CreateNewPasswordScreen (
            onNavigateToNextScreen = { }
        )
    }
}