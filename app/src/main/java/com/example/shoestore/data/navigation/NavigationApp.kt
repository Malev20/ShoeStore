package com.example.shoestore.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoestore.ui.screens.*

@Composable
fun NavigationApp(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "start_menu"
    ) {
        composable("start_menu") {
            OnboardScreen(
                onGetStartedClick = {
                    navController.navigate("sign_in")
                },
            )
        }

        composable("sign_up") {
            RegisterAccountScreen(
                onSignInClick = { navController.navigate("sign_in") },
                onSignUpClick = { navController.navigate("email_verification") }
            )
        }

        composable("sign_in") {
            SignInScreen(
                onForgotPasswordClick = { navController.navigate("forgot_password") },
                onSignUpClick = { navController.navigate("sign_up") },
                onSignInClick = {
                    navController.navigate("home") {
                        popUpTo("sign_in") { inclusive = true }
                    }
                }
            )
        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToOTP = { email -> navController.navigate("otp/$email") }
            )
        }

        composable("otp/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(
                email = email,
                onVerifySuccess = { navController.navigate("new_password") }
            )
        }

        composable("new_password") {
            CreateNewPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onSuccessNavigation = {
                    navController.navigate("sign_in") {
                        popUpTo("sign_in") { inclusive = true }
                    }
                }
            )
        }

        composable("email_verification") {
            EmailVerificationScreen(
                onSignInClick = { navController.navigate("sign_in") },
                onVerificationSuccess = { navController.navigate("home") }
            )
        }

        // HOME
        // HOME
        composable("home") {
            HomeScreen(
                onProductClick = { /* ... */ },
                onCartClick = { /* ... */ },
                onSearchClick = { /* ... */ },
                onSettingsClick = { /* больше НЕ navigate("profile") */ },
                onCategoryClick = { categoryName ->
                    if (categoryName != "All") {
                        navController.navigate("category/$categoryName")
                    }
                }
            )
        }


        composable("category/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Category"

            CategoryScreen(
                categoryName = categoryName,
                onBackClick = { navController.popBackStack() },
                onProductClick = { /* TODO */ },
                onFavoriteClick = { /* TODO */ },
                isFavorite = { false },   // ВРЕМЕННО, пока не подключишь ViewModel
                onAllClick = {
                    navController.popBackStack(route = "home", inclusive = false)
                }
            )
        }




        // PROFILE
        composable("profile") {
            ProfileScreen()
        }
    }
}
