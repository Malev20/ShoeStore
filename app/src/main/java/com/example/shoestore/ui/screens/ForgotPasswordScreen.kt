package com.example.shoestore.ui.screens
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.R
import com.example.shoestore.ui.components.BackButton
import com.example.shoestore.ui.components.DisableButton
import com.example.shoestore.ui.theme.*
import com.example.shoestore.ui.viewmodel.ForgotPasswordState
import com.example.shoestore.ui.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onNavigateToOTP: (String) -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is ForgotPasswordState.Success) {
            showDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            BackButton(onClick = onBackClick)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(id = R.string.forgot_password),
            style = AppTypography.headingRegular32
        )

        Text(
            text = stringResource(id = R.string.enter_email_to_reset),
            style = AppTypography.bodyRegular16,
            color = SubtextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; viewModel.resetState() },
            placeholder = { Text("xyz@gmail.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (state is ForgotPasswordState.Loading) {
            CircularProgressIndicator(color = Accent)
        } else {
            DisableButton(
                text = stringResource(id = R.string.send),
                onClick = {
                    if (email.contains("@")) {
                        viewModel.sendResetCode(email)
                    }
                },
                enabled = email.isNotEmpty()
            )
        }

        if (state is ForgotPasswordState.Error) {
            Text(
                text = (state as ForgotPasswordState.Error).message,
                color = Red
            )
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // 🔵 СИНИЙ КРУЖОК С ИКОНКОЙ
                    Box(
                        modifier = Modifier
                            .size(88.dp) // диаметр = 44dp * 2
                            .background(
                                color = Accent,
                                shape = RoundedCornerShape(44.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.email_alert),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Заголовок
                    Text(
                        text = stringResource(id = R.string.check_your_email),
                        style = AppTypography.bodyMedium16,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Описание
                    Text(
                        text = stringResource(id = R.string.recovery_code_sent),
                        style = AppTypography.bodyRegular14,
                        color = SubtextDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Кнопка
                    Button(
                        onClick = {
                            showDialog = false
                            onNavigateToOTP(email)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Accent),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("OK", color = Color.White)
                    }
                }
            }
        }
    }

}