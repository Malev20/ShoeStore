package com.example.shoestore.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ===== Цвета (можно потом вынести в Theme) =====
val Accent = Color(0xFF5B67CA)
val Background = Color(0xFFF8F9FF)
val Block = Color.White
val Text = Color(0xFF333333)
val SubTextDark = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // ===== Заголовок =====
        Text(
            text = "Привет!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Заполните Свои Данные",
            fontSize = 16.sp,
            color = SubTextDark
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ===== Email =====
        Text(
            text = "Email",
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
                Text("xyz@gmail.com", color = SubTextDark)
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ===== Пароль =====
        Text(
            text = "Пароль",
            fontSize = 16.sp,
            color = Text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            placeholder = {
                Text("••••••••", color = SubTextDark, fontSize = 20.sp)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = SubTextDark
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ===== Восстановить =====
        Text(
            text = "Восстановить",
            fontSize = 14.sp,
            color = SubTextDark,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== Кнопка Войти =====
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Войти",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ===== Нижний текст =====
        Text(
            text = "Вы впервые? Создать",
            fontSize = 16.sp,
            color = SubTextDark,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}
