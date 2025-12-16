package com.example.shoestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {
    // Данные формы
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // Состояния
    var agree by mutableStateOf(false)
    var passwordVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var emailError by mutableStateOf("")

    // Показать/скрыть пароль
    fun togglePassword() {
        passwordVisible = !passwordVisible
    }

    // Проверка email
    fun checkEmail() {
        val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$"
        emailError = if (Pattern.matches(pattern, email)) "" else "Неверный формат email"
    }

    // Регистрация
    fun register(onSuccess: () -> Unit) {
        // Проверяем поля
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return
        }

        if (!agree) {
            return
        }

        checkEmail()
        if (emailError.isNotEmpty()) {
            return
        }

        // Имитируем запрос
        viewModelScope.launch {
            isLoading = true
            delay(2000) // Задержка как будто отправка на сервер
            isLoading = false
            onSuccess() // Успешная регистрация
        }
    }
}