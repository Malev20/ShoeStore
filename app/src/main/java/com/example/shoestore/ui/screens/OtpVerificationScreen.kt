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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    onNavigateToNewPassword: () -> Unit
) {
    var otpCode by remember { mutableStateOf("") }
    var timerSeconds by remember { mutableStateOf(30) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var hasStartedTyping by remember { mutableStateOf(false) }

    LaunchedEffect(hasStartedTyping, isTimerRunning) {
        if (hasStartedTyping && isTimerRunning) {
            while (timerSeconds > 0 && isTimerRunning) {
                delay(1000L)
                timerSeconds--
            }
            if (timerSeconds == 0) {
                isTimerRunning = false
            }
        }
    }

    LaunchedEffect(otpCode) {
        if (otpCode.length == 6) {
            onNavigateToNewPassword()
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
            text = stringResource(R.string.twentysix),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.twentyseven),
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
                text = stringResource(R.string.twentyeight),
                fontSize = 16.sp,
                color = Text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Поле для ввода OTP кода
            OutlinedTextField(
                value = otpCode,
                onValueChange = { newText ->
                    val digits = newText.filter { it.isDigit() }
                    if (digits.length <= 6) {
                        otpCode = digits

                        // Запускаем таймер когда начали вводить
                        if (!hasStartedTyping && digits.isNotEmpty()) {
                            hasStartedTyping = true
                            isTimerRunning = true
                            timerSeconds = 30
                        }
                    }
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
                    text = if (hasStartedTyping) formatTimer(timerSeconds) else "00:30",
                    fontSize = 20.sp,
                    color = when {
                        !hasStartedTyping -> Color(0xFFA0A0A0) // Серый когда не активен
                        timerSeconds > 10 -> Accent // Синий
                        else -> Color.Red // Красный когда мало времени
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка для переотправки кода
        if (hasStartedTyping && !isTimerRunning) {
            TextButton(
                onClick = {
                    timerSeconds = 30
                    isTimerRunning = true
                    println("Запрошена переотправка OTP кода")
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
            // Просто отступ вместо кнопки
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Форматирование таймера
private fun formatTimer(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}