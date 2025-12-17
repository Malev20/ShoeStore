package com.example.shoestore.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoestore.ui.screens.*

@Composable
fun ShoeStoreNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {
        // Экран входа
        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onSignInSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        // Экран регистрации
        composable(Screen.Register.route) {
            RegisterAccountScreen(
                onNavigateToSignIn = {
                    navController.popBackStack()
                },
                onRegisterSuccess = { email ->
                    navController.navigate(Screen.OtpVerification.createRoute(email))
                }
            )
        }

        // Экран восстановления пароля
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEmailSent = { email ->
                    navController.navigate(Screen.OtpVerification.createRoute(email))
                }
            )
        }

        // Экран верификации OTP
        composable(
            route = Screen.OtpVerification.routeWithArg,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

            OtpVerificationScreen(
                email = email,
                onNavigateToNewPassword = { token ->
                    navController.navigate(Screen.CreateNewPassword.createRoute(token))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Экран создания нового пароля
        composable(
            route = Screen.CreateNewPassword.routeWithArg,
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""

            CreateNewPasswordScreen(
                authToken = token,
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        // Главный экран (после входа)
        composable(Screen.Main.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

// Определение экранов
sealed class Screen(val route: String) {
    object SignIn : Screen("sign_in")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object OtpVerification : Screen("otp_verification") {
        const val routeWithArg = "otp_verification/{email}"
        fun createRoute(email: String) = "otp_verification/$email"
    }
    object CreateNewPassword : Screen("create_new_password") {
        const val routeWithArg = "create_new_password/{token}"
        fun createRoute(token: String) = "create_new_password/$token"
    }
    object Main : Screen("main")
}