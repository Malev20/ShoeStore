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
import com.example.shoestore.ui.screens.ForgotPasswordScreen
// import com.example.shoestore.ui.screens.SignInScreen
// import com.example.shoestore.ui.screens.RegisterAccountScreen
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
                    // 🔹 ВРЕМЕННО запускаем экран восстановления пароля
                    ForgotPasswordScreen(
                        onNavigateBack = {
                            println("Возврат назад")
                        }
                    )

                    /*
                    🔹 Экран входа (вернёшь позже)
                    SignInScreen(
                        onNavigateToRegister = {
                            println("Переход на регистрацию")
                        },
                        onForgotPassword = {
                            println("Переход на восстановление пароля")
                        }
                    )
                    */

                    /*
                    🔹 Экран регистрации (вернёшь позже)
                    RegisterAccountScreen(
                        onNavigateToSignIn = {
                            println("Переход на экран входа")
                        }
                    )
                    */
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    ShoeStoreTheme {
        ForgotPasswordScreen(
            onNavigateBack = {}
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    ShoeStoreTheme {
        SignInScreen(
            onNavigateToRegister = {},
            onForgotPassword = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    ShoeStoreTheme {
        RegisterAccountScreen(
            onNavigateToSignIn = {}
        )
    }
}
*/