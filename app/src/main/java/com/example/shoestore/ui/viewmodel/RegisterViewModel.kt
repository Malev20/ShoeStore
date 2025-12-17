package com.example.shoestore.ui.viewmodel
import com.example.shoestore.data.model.SignUpRequest
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoestore.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class RegisterViewModel : ViewModel() {
    // Данные формы
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // Состояния UI
    var agree by mutableStateOf(false)
    var passwordVisible by mutableStateOf(false)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var nameError by mutableStateOf<String?>(null)

    // Состояния запросов
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    // Показать/скрыть пароль
    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    // Валидация email
    fun validateEmail(): Boolean {
        val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$".toRegex()
        return when {
            email.isEmpty() -> {
                emailError = "Email не может быть пустым"
                false
            }
            !pattern.matches(email) -> {
                emailError = "Неверный формат email. Пример: name@domain.ru"
                false
            }
            else -> {
                emailError = null
                true
            }
        }
    }

    // Валидация имени
    fun validateName(): Boolean {
        return when {
            name.isEmpty() -> {
                nameError = "Имя не может быть пустым"
                false
            }
            else -> {
                nameError = null
                true
            }
        }
    }

    // Валидация пароля
    fun validatePassword(): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Пароль не может быть пустым"
                false
            }
            password.length < 6 -> {
                passwordError = "Пароль должен содержать минимум 6 символов"
                false
            }
            else -> {
                passwordError = null
                true
            }
        }
    }

    // Валидация всей формы
    fun validateForm(): Boolean {
        val isNameValid = validateName()
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()

        return isNameValid && isEmailValid && isPasswordValid && agree
    }

    // Регистрация пользователя
    fun register() {
        if (!validateForm()) {
            _registerState.value = RegisterState.Error("Пожалуйста, заполните все поля корректно")
            return
        }

        if (!agree) {
            _registerState.value = RegisterState.Error("Необходимо согласие с условиями")
            return
        }

        _isLoading.value = true
        _registerState.value = RegisterState.Loading

        viewModelScope.launch {
            try {
                Log.d("RegisterViewModel", "Отправка запроса регистрации для: $email")

                // Проверяем, какие поля нужны для SignUpRequest
                val signUpRequest = SignUpRequest(
                    email = email,
                    password = password,
                    // user_metadata может не поддерживаться вашей версией API
                    // Пробуем без него или с data
                    data = mapOf("name" to name) // ИСПРАВЛЕНО!
                )

                val response = RetrofitInstance.userManagementService.signUp(signUpRequest)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("RegisterViewModel", "Регистрация успешна: ${it.id}")
                        _registerState.value = RegisterState.Success
                    } ?: run {
                        _registerState.value = RegisterState.Error("Пустой ответ от сервера")
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = parseRegisterError(errorCode, errorBody)

                    Log.e("RegisterViewModel", "Ошибка регистрации: $errorCode - $errorMessage")
                    _registerState.value = RegisterState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is ConnectException -> "Отсутствует соединение с интернетом"
                    is SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                Log.e("RegisterViewModel", "Ошибка: $errorMessage", e)
                _registerState.value = RegisterState.Error(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Парсинг ошибок регистрации
    private fun parseRegisterError(code: Int, errorBody: String): String {
        return when (code) {
            400 -> "Некорректный запрос"
            422 -> when {
                errorBody.contains("email", ignoreCase = true) -> "Неверный формат email"
                errorBody.contains("password", ignoreCase = true) -> "Пароль слишком слабый"
                else -> "Ошибка валидации данных"
            }
            429 -> "Слишком много запросов. Попробуйте позже"
            500, 502, 503 -> "Ошибка сервера. Попробуйте позже"
            else -> "Ошибка регистрации: $errorBody"
        }
    }

    // Сброс состояния
    fun resetState() {
        _registerState.value = RegisterState.Idle
        _isLoading.value = false
    }

    // Очистка полей
    fun clearFields() {
        name = ""
        email = ""
        password = ""
        agree = false
        passwordVisible = false
        emailError = null
        passwordError = null
        nameError = null
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}