package com.example.shoestore.ui.screens
import androidx.lifecycle.viewmodel.compose.viewModel
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
    authToken: String,
    onNavigateToSignIn: () -> Unit
) {
    val viewModel: com.example.shoestore.ui.viewmodel.CreateNewPasswordViewModel = viewModel()

    // Устанавливаем токен
    LaunchedEffect(authToken) {
        viewModel.setAuthToken(authToken)
    }

    // Наблюдаем за состоянием изменения пароля
    LaunchedEffect(viewModel.createPasswordState) {
        when (val state = viewModel.createPasswordState.value) {
            is com.example.shoestore.ui.viewmodel.CreatePasswordState.Success -> {
                onNavigateToSignIn()
                viewModel.resetState()
            }
            else -> {}
        }
    }

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
                Text(
                    text = "Введите пароль",
                    color = SubTextDark,
                    fontSize = 16.sp
                )
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        imageVector = if (viewModel.passwordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (viewModel.passwordVisible)
                            "Скрыть пароль"
                        else
                            "Показать пароль",
                        tint = SubTextDark
                    )
                }
            },
            isError = viewModel.passwordError != null,
            supportingText = {
                viewModel.passwordError?.let { error ->
                    Text(text = error, color = Color.Red)
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
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            visualTransformation = if (viewModel.confirmPasswordVisible)
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
                IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                    Icon(
                        imageVector = if (viewModel.confirmPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (viewModel.confirmPasswordVisible)
                            "Скрыть пароль"
                        else
                            "Показать пароль",
                        tint = SubTextDark
                    )
                }
            },
            isError = viewModel.confirmPasswordError != null,
            supportingText = {
                viewModel.confirmPasswordError?.let { error ->
                    Text(text = error, color = Color.Red)
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

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка "Сохранить"
        Button(
            onClick = {
                viewModel.changePassword()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = viewModel.validateForm() && !viewModel.isLoading.value,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (viewModel.validateForm() && !viewModel.isLoading.value)
                    Accent else Color(0xFF2B6B8B),
                disabledContainerColor = Color(0xFF2B6B8B)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            if (viewModel.isLoading.value) {
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