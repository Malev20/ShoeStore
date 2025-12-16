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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Заголовок - ХАРДКОД как в RegisterAccountScreen
        Text(
            text = "Забыл пароль", // Хардкод вместо stringResource(R.string.twentyone)
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Подзаголовок - ХАРДКОД
        Text(
            text = "Введите Свою Учетную Запись\n Для Сброса", // Хардкод вместо stringResource(R.string.twentytwo)
            fontSize = 16.sp,
            color = SubTextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email - ХАРДКОД
        Text(
            text = "Email", // Хардкод вместо stringResource(R.string.nine)
            fontSize = 16.sp,
            color = Text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
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

        // Кнопка - ХАРДКОД
        Button(
            onClick = {
                // TODO: Логика отправки email для сброса пароля
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Отправить", // Хардкод вместо stringResource(R.string.twentythree)
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}