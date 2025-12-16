package com.example.shoestore.viewmodels

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

    // Состояния UI
    var agreeToTerms by mutableStateOf(false)
    var passwordVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    // Ошибки валидации
    var nameError by mutableStateOf("")
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")

    // Переключение видимости пароля
    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    // Валидация email
    fun validateEmail(): Boolean {
        val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$"
        return if (email.isEmpty()) {
            emailError = "Введите email"
            false
        } else if (!Pattern.matches(pattern, email)) {
            emailError = "Неверный формат email. Пример: name@domain.ru"
            false
        } else {
            emailError = ""
            true
        }
    }

    // Валидация имени
    fun validateName(): Boolean {
        return if (name.isEmpty()) {
            nameError = "Введите ваше имя"
            false
        } else {
            nameError = ""
            true
        }
    }

    // Валидация пароля
    fun validatePassword(): Boolean {
        return if (password.isEmpty()) {
            passwordError = "Введите пароль"
            false
        } else if (password.length < 6) {
            passwordError = "Пароль должен быть не менее 6 символов"
            false
        } else {
            passwordError = ""
            true
        }
    }

    // Основная функция регистрации
    fun register(onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Валидация всех полей
        val isNameValid = validateName()
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()

        if (!isNameValid || !isEmailValid || !isPasswordValid) {
            onError("Пожалуйста, исправьте ошибки в форме")
            return
        }

        if (!agreeToTerms) {
            onError("Для регистрации необходимо согласие на обработку персональных данных")
            return
        }

        // Имитация регистрации через API
        viewModelScope.launch {
            isLoading = true

            try {
                // Имитация сетевого запроса (2 секунды)
                delay(2000)

                // Здесь в реальном приложении будет вызов API
                // val response = apiService.register(name, email, password)

                // Предположим успешную регистрацию
                onSuccess()

            } catch (e: Exception) {
                // Обработка ошибок сети
                val errorMessage = when {
                    e.message?.contains("network", ignoreCase = true) == true ->
                        "Нет соединения с интернетом"
                    e.message?.contains("timeout", ignoreCase = true) == true ->
                        "Время ожидания истекло"
                    else -> "Ошибка регистрации: ${e.message}"
                }
                onError(errorMessage)
            } finally {
                isLoading = false
            }
        }
    }
}