package com.example.shoestore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.shoestore.R
import com.example.shoestore.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String,
    onNavigateToNewPassword: (token: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: com.example.shoestore.ui.viewmodel.OtpVerificationViewModel = viewModel()

    // Устанавливаем email при создании
    LaunchedEffect(Unit) {
        viewModel.setEmailForVerification(email)
    }

    // Наблюдаем за состоянием OTP
    LaunchedEffect(viewModel.otpState) {
        when (val state = viewModel.otpState.value) {
            is com.example.shoestore.ui.viewmodel.OtpState.Success -> {
                // В реальном приложении здесь нужно получить токен
                // Пока используем OTP как токен
                onNavigateToNewPassword(viewModel.otpCode)
                viewModel.resetState()
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

        Text(
            text = "Проверьте Ваш Email",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Мы отправили код восстановления пароля на вашу электронную почту.",
            fontSize = 16.sp,
            color = SubTextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Код",
                fontSize = 16.sp,
                color = Text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Поле для ввода OTP кода
            OutlinedTextField(
                value = viewModel.otpCode,
                onValueChange = { newText ->
                    viewModel.updateOtpCode(newText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = {
                    Text(
                        text = "Введите код",
                        color = SubTextDark,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Text
                ),
                isError = viewModel.showOtpError,
                supportingText = {
                    if (viewModel.showOtpError) {
                        Text(text = viewModel.otpError ?: "Ошибка", color = Color.Red)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Accent,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Block,
                    unfocusedContainerColor = Block,
                    cursorColor = Accent,
                    unfocusedTextColor = Text,
                    focusedTextColor = Text
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Таймер
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (viewModel.hasStartedTyping.value)
                        viewModel.formatTimer()
                    else
                        "00:30",
                    fontSize = 20.sp,
                    color = when {
                        !viewModel.hasStartedTyping.value -> Color(0xFFA0A0A0)
                        viewModel.timerSeconds.value > 10 -> Accent
                        else -> Color.Red
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка для переотправки кода
        if (viewModel.hasStartedTyping.value && !viewModel.isTimerRunning.value) {
            TextButton(
                onClick = {
                    viewModel.resendOtp()
                },
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Отправить код повторно",
                    fontSize = 14.sp,
                    color = Accent,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}