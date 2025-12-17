package com.example.shoestore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoestore.ui.screens.*

@Composable
fun ShoeStoreNavigation() {
    val navController = rememberNavController()
    var pendingEmail by remember { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.name
    ) {
        composable(Screen.SignIn.name) {
            SignInScreen(

            )
        }

        composable(Screen.Register.name) {
            RegisterAccountScreen(
                onNavigateToSignIn = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ForgotPassword.name) {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.OtpVerification.name) {
            LaunchedEffect(pendingEmail) {
                if (pendingEmail != null) {
                    println("Email для OTP: $pendingEmail")
                    pendingEmail = null
                }
            }

            OtpVerificationScreen(
                onNavigateToNewPassword = {
                    navController.navigate(Screen.CreateNewPassword.name)
                }
            )
        }

        composable(Screen.CreateNewPassword.name) {
            CreateNewPasswordScreen(
                onNavigateToNextScreen = {
                    navController.navigate(Screen.SignIn.name) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.Main.name) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.SignIn.name) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

enum class Screen {
    SignIn,
    Register,
    ForgotPassword,
    OtpVerification,
    CreateNewPassword,
    Main
}

// Заглушка для главного экрана
@Composable
fun MainScreen(onLogout: () -> Unit) {
    androidx.compose.material3.Text("Главный экран приложения")
}