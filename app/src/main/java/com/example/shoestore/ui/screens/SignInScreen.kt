package com.example.shoestore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.ui.viewmodel.SignInViewModel

// Цвета
val Accent = Color(0xFF48B2E7)
val Background = Color(0xFFF8F9FF)
val Block = Color.White
val TextColor = Color(0xFF333333)
val SubTextDark = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onSignInSuccess: () -> Unit
) {
    val viewModel: SignInViewModel = viewModel()

    val isLoading by viewModel.isLoading.collectAsState()
    val signInState by viewModel.signInState.collectAsState()

    LaunchedEffect(signInState) {
        if (signInState is com.example.shoestore.ui.viewmodel.SignInState.Success) {
            onSignInSuccess()
            viewModel.resetState()
        }
    }

    // КОРНЕВОЙ КОНТЕЙНЕР БЕЗ PADDING
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // ОСНОВНОЙ КОНТЕНТ С PADDING
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Привет!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Заполните свои данные",
                fontSize = 16.sp,
                color = SubTextDark
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ===== Email =====
            Text(
                text = "Email",
                fontSize = 16.sp,
                color = TextColor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                singleLine = true,
                placeholder = {
                    Text("xyz@gmail.com", color = SubTextDark)
                },
                textStyle = LocalTextStyle.current.copy(
                    color = TextColor,
                    fontSize = 16.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Accent,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Block,
                    unfocusedContainerColor = Block,
                    cursorColor = Accent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ===== Пароль =====
            Text(
                text = "Пароль",
                fontSize = 16.sp,
                color = TextColor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                singleLine = true,
                visualTransformation = if (viewModel.passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                placeholder = {
                    Text("••••••••", color = SubTextDark)
                },
                textStyle = LocalTextStyle.current.copy(
                    color = TextColor,
                    fontSize = 16.sp
                ),
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            imageVector = if (viewModel.passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = SubTextDark
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Accent,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Block,
                    unfocusedContainerColor = Block,
                    cursorColor = Accent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Восстановить",
                fontSize = 14.sp,
                color = Accent,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onNavigateToForgotPassword() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== КНОПКА =====
            Button(
                onClick = { viewModel.signIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "Войти",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Вы впервые? ",
                    fontSize = 16.sp,
                    color = SubTextDark
                )
                Text(
                    text = "Создать",
                    fontSize = 16.sp,
                    color = Accent,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}
