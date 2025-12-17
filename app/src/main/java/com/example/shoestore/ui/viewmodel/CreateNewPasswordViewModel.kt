package com.example.shoestore.ui.viewmodel

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
import com.example.shoestore.data.model.ChangePasswordRequest
class CreateNewPasswordViewModel : ViewModel() {
    // Данные формы
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    // Ошибки валидации
    var passwordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)

    // Токен для изменения пароля
    private var authToken: String? = null

    // Состояния запросов
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _createPasswordState = MutableStateFlow<CreatePasswordState>(CreatePasswordState.Idle)
    val createPasswordState: StateFlow<CreatePasswordState> = _createPasswordState.asStateFlow()

    // Показать/скрыть пароль
    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible
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

    // Валидация подтверждения пароля
    fun validateConfirmPassword(): Boolean {
        return when {
            confirmPassword.isEmpty() -> {
                confirmPasswordError = "Подтвердите пароль"
                false
            }
            password != confirmPassword -> {
                confirmPasswordError = "Пароли не совпадают"
                false
            }
            else -> {
                confirmPasswordError = null
                true
            }
        }
    }

    // Валидация всей формы
    fun validateForm(): Boolean {
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()
        return isPasswordValid && isConfirmPasswordValid
    }

    // Установка токена
    fun setAuthToken(token: String) {
        this.authToken = token
    }

    // Изменение пароля
    fun changePassword() {
        if (!validateForm()) {
            _createPasswordState.value = CreatePasswordState.Error("Пожалуйста, проверьте введенные данные")
            return
        }

        val token = authToken
        if (token.isNullOrEmpty()) {
            _createPasswordState.value = CreatePasswordState.Error("Ошибка авторизации")
            return
        }

        _isLoading.value = true
        _createPasswordState.value = CreatePasswordState.Loading

        viewModelScope.launch {
            try {
                Log.d("CreateNewPasswordViewModel", "Изменение пароля")

                val response = RetrofitInstance.userManagementService.changePassword(
                    token = "Bearer $token",
                    changePasswordRequest = ChangePasswordRequest(password = password)
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("CreateNewPasswordViewModel", "Пароль успешно изменен")
                        _createPasswordState.value = CreatePasswordState.Success
                    } ?: run {
                        _createPasswordState.value = CreatePasswordState.Error("Пустой ответ от сервера")
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = parseChangePasswordError(errorCode, errorBody)

                    Log.e("CreateNewPasswordViewModel", "Ошибка изменения пароля: $errorCode - $errorMessage")
                    _createPasswordState.value = CreatePasswordState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is ConnectException -> "Отсутствует соединение с интернетом"
                    is SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                Log.e("CreateNewPasswordViewModel", "Ошибка: $errorMessage", e)
                _createPasswordState.value = CreatePasswordState.Error(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Парсинг ошибок изменения пароля
    private fun parseChangePasswordError(code: Int, errorBody: String): String {
        return when (code) {
            400 -> "Некорректный запрос"
            401 -> "Неавторизованный доступ"
            422 -> when {
                errorBody.contains("password", ignoreCase = true) -> "Пароль не соответствует требованиям"
                else -> "Ошибка валидации данных"
            }
            429 -> "Слишком много запросов. Попробуйте позже"
            500, 502, 503 -> "Ошибка сервера. Попробуйте позже"
            else -> "Ошибка изменения пароля: $errorBody"
        }
    }

    // Сброс состояния
    fun resetState() {
        _createPasswordState.value = CreatePasswordState.Idle
        _isLoading.value = false
    }

    // Очистка полей
    fun clearFields() {
        password = ""
        confirmPassword = ""
        passwordVisible = false
        confirmPasswordVisible = false
        passwordError = null
        confirmPasswordError = null
    }
}

sealed class CreatePasswordState {
    object Idle : CreatePasswordState()
    object Loading : CreatePasswordState()
    object Success : CreatePasswordState()
    data class Error(val message: String) : CreatePasswordState()
}