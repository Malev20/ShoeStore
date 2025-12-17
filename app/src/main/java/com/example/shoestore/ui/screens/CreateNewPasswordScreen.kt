package com.example.shoestore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview // <-- Добавьте эту строку
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoestore.R
import com.example.shoestore.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewPasswordScreen(
    onNavigateToNextScreen: () -> Unit
) {
    // Состояния полей ввода
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Флаг для проверки совпадения паролей
    val passwordsMatch = password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
    val isButtonEnabled = password.isNotEmpty() && confirmPassword.isNotEmpty() && passwordsMatch && !isLoading

    // Цвета
    val activeButtonColor = Accent
    val inactiveButtonColor = Color(0xFF2B6B8B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // Заголовок "Задать Новый Пароль"
        Text(
            text = "Задать Новый Пароль",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Подзаголовок
        Text(
            text = "Установите Новый Пароль Для Входа\nВ Вашу Учетную Запись",
            fontSize = 16.sp,
            color = SubTextDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        // Поле "Пароль"
        Text(
            text = "Пароль",
            color = Text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
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
                Text(
                    text = "Введите пароль",
                    color = SubTextDark,
                    fontSize = 16.sp
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible)
                            "Скрыть пароль"
                        else
                            "Показать пароль",
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

        Spacer(modifier = Modifier.height(16.dp))

        // Поле "Подтверждение пароля"
        Text(
            text = "Подтверждение пароля",
            color = Text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = "Подтвердите пароль",
                    color = SubTextDark,
                    fontSize = 16.sp
                )
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (confirmPasswordVisible)
                            "Скрыть пароль"
                        else
                            "Показать пароль",
                        tint = SubTextDark
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                focusedIndicatorColor = if (!passwordsMatch && confirmPassword.isNotEmpty())
                    Color.Red
                else
                    Color.Transparent,
                unfocusedIndicatorColor = if (!passwordsMatch && confirmPassword.isNotEmpty())
                    Color.Red
                else
                    Color.Transparent,
                cursorColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        // Ошибка несовпадения паролей
        if (confirmPassword.isNotEmpty() && !passwordsMatch) {
            Text(
                text = "Пароли не совпадают",
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка "Сохранить"
        Button(
            onClick = {
                isLoading = true
                // Симуляция загрузки
                // В реальном приложении здесь будет API-запрос
                // После успеха переходим на следующий экран
                onNavigateToNextScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isButtonEnabled) activeButtonColor else inactiveButtonColor,
                disabledContainerColor = inactiveButtonColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Сохранение...", color = Color.White)
            } else {
                Text(
                    text = "Сохранить",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun CreateNewPasswordScreenPreview() {
    ShoeStoreTheme {
        CreateNewPasswordScreen(
            onNavigateToNextScreen = {}
        )
    }
}