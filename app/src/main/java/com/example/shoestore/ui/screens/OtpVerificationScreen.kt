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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.ui.theme.*
import com.example.shoestore.ui.viewmodel.OtpVerificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String,
    onNavigateToNewPassword: (token: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: OtpVerificationViewModel = viewModel()

    val timerSeconds by viewModel.timerSeconds.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()
    val hasStartedTyping by viewModel.hasStartedTyping.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val otpState by viewModel.otpState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEmailForVerification(email)
    }

    LaunchedEffect(otpState) {
        if (otpState is com.example.shoestore.ui.viewmodel.OtpState.Success) {
            onNavigateToNewPassword(viewModel.otpCode)
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

        Text(
            text = "Проверьте Ваш Email",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
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

        Text(
            text = "Код",
            fontSize = 16.sp,
            color = TextColor,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.otpCode,
            onValueChange = { viewModel.updateOtpCode(it) },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp), // ✅ FIX
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    "Введите код",
                    color = SubTextDark,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = TextColor
            ),
            isError = viewModel.showOtpError,
            supportingText = {
                if (viewModel.showOtpError) {
                    Text(viewModel.otpError ?: "Ошибка", color = Color.Red)
                }
            },
            colors = outlinedColors(),
            shape = RoundedCornerShape(12.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            val formattedTime = if (hasStartedTyping) {
                val minutes = timerSeconds / 60
                val seconds = timerSeconds % 60
                String.format("%02d:%02d", minutes, seconds)
            } else "00:30"

            Text(
                text = formattedTime,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = when {
                    !hasStartedTyping -> Color(0xFFA0A0A0)
                    timerSeconds > 10 -> Accent
                    else -> Color.Red
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (hasStartedTyping && !isTimerRunning) {
            TextButton(
                onClick = { viewModel.resendOtp() },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outlinedColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Accent,
    unfocusedBorderColor = Color(0xFFE0E0E0),
    focusedContainerColor = Block,
    unfocusedContainerColor = Block,
    cursorColor = Accent,
    focusedTextColor = TextColor,
    unfocusedTextColor = TextColor
)
