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
import com.example.shoestore.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit = {},
    onEmailSent: (email: String) -> Unit
) {
    val viewModel: com.example.shoestore.ui.viewmodel.ForgotPasswordViewModel = viewModel()

    // Показываем диалог при успешной отправке
    if (viewModel.showEmailSentDialog.value) {
        EmailSentAlertDialog(
            onDismissRequest = {
                viewModel.closeEmailSentDialog()
                onEmailSent(viewModel.email)
            }
        )
    }

    // Наблюдаем за состоянием отправки
    LaunchedEffect(viewModel.forgotPasswordState) {
        when (val state = viewModel.forgotPasswordState.value) {
            is com.example.shoestore.ui.viewmodel.ForgotPasswordState.Success -> {
                // Диалог показывается через showEmailSentDialog
            }
            else -> {}
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
                .height(56.dp),
            placeholder = {
                Text(
                    text = "xyz@gmail.com",
                    color = SubTextDark
                )
            },
            singleLine = true,
            isError = viewModel.emailError != null,
            supportingText = {
                viewModel.emailError?.let { error ->
                    Text(text = error, color = Color.Red)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                cursorColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
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
            if (viewModel.isLoading.value) {
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