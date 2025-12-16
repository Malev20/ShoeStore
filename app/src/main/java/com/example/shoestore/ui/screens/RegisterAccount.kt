package com.example.shoestore.ui.screens
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoestore.R
import com.example.shoestore.ui.theme.*

@Composable
fun RegisterAccountScreen(
    onNavigateToSignIn: () -> Unit
) {
    // Состояния полей ввода
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }

    // Цвета
    val activeButtonColor = Accent // Активная кнопка (синий)
    val inactiveButtonColor = Color(0xFF2B6B8B) // Неактивная кнопка (#2B6B8B)
    val linkColor = Color(0xFF6A6A6A) // Цвет ссылки (#6A6A6A)

    // Функция проверки email
    fun checkEmail(email: String): String {
        val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$"
        return if (email.isNotEmpty() && !email.matches(pattern.toRegex())) {
            "Неверный формат email. Пример: name@domain.ru"
        } else {
            ""
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

        // Заголовок "Регистрация" по центру
        Text(
            text = "Регистрация",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Подзаголовок "Заполните Свои Данные" по центру
        Text(
            text = "Заполните Свои Данные",
            fontSize = 16.sp,
            color = SubTextDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Поле "Ваше имя"
        Text(
            text = "Ваше имя",
            color = Text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
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

        // Поле "Email"
        Text(
            text = "Email",
            color = Text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = checkEmail(it)
            },
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
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                focusedIndicatorColor = if (emailError.isNotEmpty()) Color.Red else Color.Transparent,
                unfocusedIndicatorColor = if (emailError.isNotEmpty()) Color.Red else Color.Transparent,
                cursorColor = Accent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        // Ошибка email (если есть)
        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                    text = "•••••••",
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

        // Чекбокс согласия С ИКОНКОЙ и подчеркиванием
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Кастомный чекбокс с иконкой
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        if (agree) Accent else Color.Transparent,
                        RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (agree) Accent else SubTextDark,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { agree = !agree }
            ) {
                if (agree) {
                    // Показываем иконку галочки
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

            // Текст согласия с подчеркиванием
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = SubTextDark,
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.Underline // Подчеркивание
                        )
                    ) {
                        append("Даю согласие на обработку\nперсональных данных")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка "Зарегистрироваться" с кастомным цветом неактивного состояния
        Button(
            onClick = {
                // Здесь будет логика регистрации
                isLoading = true
                // Имитация запроса на сервер
                // В реальном приложении здесь будет вызов API
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = agree && !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (agree && !isLoading) activeButtonColor else inactiveButtonColor,
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

        Spacer(modifier = Modifier.height(24.dp))

        // Текст "Есть аккаунт? Войти" (не кнопка, а кликабельный текст) цвета #6A6A6A
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = linkColor, // #6A6A6A
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None
                    )
                ) {
                    append("Есть аккаунт? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = linkColor, // #6A6A6A
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline, // Подчеркивание
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