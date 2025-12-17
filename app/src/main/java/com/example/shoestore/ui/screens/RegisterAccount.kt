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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoestore.R
import com.example.shoestore.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun RegisterAccountScreen(
    onNavigateToSignIn: () -> Unit,
    onRegisterSuccess: (email: String) -> Unit
) {
    val viewModel: com.example.shoestore.ui.viewmodel.RegisterViewModel = viewModel()

    // Наблюдаем за состоянием регистрации
    LaunchedEffect(viewModel.registerState) {
        when (val state = viewModel.registerState.value) {
            is com.example.shoestore.ui.viewmodel.RegisterState.Success -> {
                onRegisterSuccess(viewModel.email)
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

        // "Регистрация"
        Text(
            text = "Регистрация",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Заполните Свои Данные",
            fontSize = 16.sp,
            color = SubTextDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Ваше имя",
            color = Text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "XXXXXXXX",
                    color = SubTextDark,
                    fontSize = 16.sp
                )
            },
            isError = viewModel.nameError != null,
            supportingText = {
                viewModel.nameError?.let { error ->
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

        Text(
            text = "Email",
            color = Text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "xyz@gmail.com",
                    color = SubTextDark,
                    fontSize = 16.sp
                )
            },
            isError = viewModel.emailError != null,
            supportingText = {
                viewModel.emailError?.let { error ->
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
                    text = "•••••••",
                    color = SubTextDark,
                    fontSize = 20.sp
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        if (viewModel.agree) Accent else Color.Transparent,
                        RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (viewModel.agree) Accent else SubTextDark,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { viewModel.agree = !viewModel.agree }
            ) {
                if (viewModel.agree) {
                    Image(
                        painter = painterResource(id = R.drawable.policy_check),
                        contentDescription = "Согласие принято",
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = SubTextDark,
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Даю согласие на обработку\nперсональных данных")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.register()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = viewModel.agree && !viewModel.isLoading.value,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (viewModel.agree && !viewModel.isLoading.value)
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
                Text("Регистрация...", color = Color.White)
            } else {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(133.dp))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None
                    )
                ) {
                    append("Есть аккаунт? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Войти")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToSignIn() }
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}