package com.example.shoestore.navigation  // ← ВАЖНО!

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoestore.ui.screens.*
import com.example.shoestore.ui.screens.SignInScreen
import com.example.shoestore.ui.screens.RegisterAccountScreen
import com.example.shoestore.ui.screens.ForgotPasswordScreen
import com.example.shoestore.ui.screens.OtpVerificationScreen
import com.example.shoestore.ui.screens.CreateNewPasswordScreen
@Composable
fun ShoeStoreNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "sign_in"
    ) {
        composable("sign_in") {
            SignInScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToForgotPassword = { navController.navigate("forgot_password") },
                onSignInSuccess = {
                    // Успешный вход
                    println("Вход успешен!")
                }
            )
        }

        composable("register") {
            RegisterAccountScreen(
                onNavigateToSignIn = { navController.popBackStack() },
                onRegisterSuccess = { email ->
                    navController.navigate("otp_verification")
                }
            )
        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() },
                onEmailSent = { email ->
                    navController.navigate("otp_verification")
                }
            )
        }

        composable("otp_verification") {
            OtpVerificationScreen(
                email = "",
                onNavigateToNewPassword = { token ->
                    navController.navigate("create_password")
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("create_password") {
            CreateNewPasswordScreen(
                authToken = "",
                onNavigateToSignIn = {
                    navController.navigate("sign_in") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}