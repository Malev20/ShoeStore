package com.example.shoestore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.ui.theme.*
import com.example.shoestore.ui.viewmodel.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit = {},
    onEmailSent: (email: String) -> Unit
) {
    val viewModel: ForgotPasswordViewModel = viewModel()

    // Используем collectAsState
    val isLoading by viewModel.isLoading.collectAsState()
    val showEmailSentDialog by viewModel.showEmailSentDialog.collectAsState()
    val forgotPasswordState by viewModel.forgotPasswordState.collectAsState()

    // Наблюдаем за состоянием отправки
    LaunchedEffect(forgotPasswordState) {
        if (forgotPasswordState is com.example.shoestore.ui.viewmodel.ForgotPasswordState.Success) {
            onEmailSent(viewModel.email)
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Заголовок
        Text(
            text = "Забыл пароль",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Подзаголовок
        Text(
            text = "Введите Свою Учетную Запись\n Для Сброса",
            fontSize = 16.sp,
            color = SubTextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email
        Text(
            text = "Email",
            fontSize = 16.sp,
            color = Text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp), // ✅ ВМЕСТО height
            singleLine = true,
            placeholder = {
                Text("xyz@gmail.com", color = SubTextDark)
            },
            textStyle = LocalTextStyle.current.copy(
                color = Text,
                fontSize = 16.sp
            ),
            isError = viewModel.emailError != null,
            supportingText = {
                viewModel.emailError?.let { error ->
                    Text(text = error, color = Color.Red)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Accent,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                cursorColor = Accent,
                focusedTextColor = Text,
                unfocusedTextColor = Text
            ),
            shape = RoundedCornerShape(12.dp)
        )


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.sendRecoveryEmail()
            },
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
                    text = "Отправить",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}