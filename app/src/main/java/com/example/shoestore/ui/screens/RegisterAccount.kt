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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.R
import com.example.shoestore.ui.theme.*
import com.example.shoestore.ui.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAccountScreen(
    onNavigateToSignIn: () -> Unit,
    onRegisterSuccess: (email: String) -> Unit
) {
    val viewModel: RegisterViewModel = viewModel()

    val isLoading by viewModel.isLoading.collectAsState()
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        if (registerState is com.example.shoestore.ui.viewmodel.RegisterState.Success) {
            onRegisterSuccess(viewModel.email)
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

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

        // ===== ИМЯ =====
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Ваше имя", color = Text, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 56.dp),
                singleLine = true,
                placeholder = { Text("XXXXXXXX", color = SubTextDark) },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = Text
                ),
                isError = viewModel.nameError != null,
                supportingText = {
                    viewModel.nameError?.let {
                        Text(it, color = Color.Red)
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
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ===== EMAIL =====
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Email", color = Text, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 56.dp),
                singleLine = true,
                placeholder = { Text("xyz@gmail.com", color = SubTextDark) },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = Text
                ),
                isError = viewModel.emailError != null,
                supportingText = {
                    viewModel.emailError?.let {
                        Text(it, color = Color.Red)
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== ПАРОЛЬ =====
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Пароль", color = Text, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 56.dp),
                singleLine = true,
                visualTransformation =
                    if (viewModel.passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                placeholder = { Text("•••••••", color = SubTextDark, fontSize = 20.sp) },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = Text
                ),
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            imageVector = if (viewModel.passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                            contentDescription = null,
                            tint = SubTextDark
                        )
                    }
                },
                isError = viewModel.passwordError != null,
                supportingText = {
                    viewModel.passwordError?.let {
                        Text(it, color = Color.Red)
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== CHECKBOX =====
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
                        1.dp,
                        if (viewModel.agree) Accent else SubTextDark,
                        RoundedCornerShape(4.dp)
                    )
                    .clickable { viewModel.agree = !viewModel.agree }
            ) {
                if (viewModel.agree) {
                    Image(
                        painter = painterResource(R.drawable.policy_check),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Даю согласие на обработку\nперсональных данных",
                color = SubTextDark,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.register() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = viewModel.agree && !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Accent,
                disabledContainerColor = Color(0xFF2B6B8B)
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
                    "Зарегистрироваться",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(113.dp))

        Text(
            text = "Есть аккаунт? Войти",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToSignIn() },
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = SubTextDark,
            fontWeight = FontWeight.Bold
        )
    }
}