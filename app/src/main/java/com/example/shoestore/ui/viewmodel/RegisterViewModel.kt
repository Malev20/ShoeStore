package com.example.shoestore.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    // UI состояния
    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var agree = mutableStateOf(false)
    var passwordVisible = mutableStateOf(false)

    // Ошибки валидации
    var nameError = mutableStateOf("")
    var emailError = mutableStateOf("")
    var passwordError = mutableStateOf("")

    // Состояние загрузки
    var isLoading = mutableStateOf(false)

    // Валидация имени
    fun validateName() {
        nameError.value = when {
            name.value.isEmpty() -> "Имя не может быть пустым"
            name.value.length < 2 -> "Имя должно быть не менее 2 символов"
            !name.value.matches(Regex("^[a-zA-Zа-яА-ЯёЁ\\s-]+$")) -> "Имя содержит недопустимые символы"
            else -> ""
        }
    }

    // Валидация email
    fun validateEmail() {
        val pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        emailError.value = when {
            email.value.isEmpty() -> "Email не может быть пустым"
            !email.value.matches(pattern.toRegex()) -> "Неверный формат email"
            else -> ""
        }
    }

    // Валидация пароля
    fun validatePassword() {
        passwordError.value = when {
            password.value.isEmpty() -> "Пароль не может быть пустым"
            password.value.length < 6 -> "Пароль должен быть не менее 6 символов"
            !password.value.matches(Regex(".*[A-Z].*")) -> "Пароль должен содержать хотя бы одну заглавную букву"
            !password.value.matches(Regex(".*\\d.*")) -> "Пароль должен содержать хотя бы одну цифру"
            else -> ""
        }
    }

    // Переключение видимости пароля
    fun togglePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }

    // Проверка всех полей
    fun validateAll(): Boolean {
        validateName()
        validateEmail()
        validatePassword()
        return nameError.value.isEmpty() &&
                emailError.value.isEmpty() &&
                passwordError.value.isEmpty() &&
                agree.value
    }

    // Симуляция регистрации
    suspend fun register(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!validateAll()) {
            onError("Пожалуйста, исправьте ошибки в форме")
            return
        }

        isLoading.value = true
        try {
            // Имитация сетевого запроса
            delay(2000)

            // Здесь будет реальный API вызов
            // val response = api.register(name.value, email.value, password.value)

            // Проверка успешности (имитация)
            val isSuccess = true // Замени на реальную проверку

            if (isSuccess) {
                onSuccess()
            } else {
                onError("Ошибка регистрации. Попробуйте позже")
            }
        } catch (e: Exception) {
            onError("Ошибка сети: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }
}