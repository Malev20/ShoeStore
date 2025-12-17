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
import com.example.shoestore.data.model.ForgotPasswordRequest
class ForgotPasswordViewModel : ViewModel() {
    // Данные формы
    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)

    // Состояния запросов
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState.asStateFlow()

    private val _showEmailSentDialog = MutableStateFlow(false)
    val showEmailSentDialog: StateFlow<Boolean> = _showEmailSentDialog.asStateFlow()

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

    // Отправка запроса на восстановление пароля
    fun sendRecoveryEmail() {
        if (!validateEmail()) {
            _forgotPasswordState.value = ForgotPasswordState.Error("Пожалуйста, введите корректный email")
            return
        }

        _isLoading.value = true
        _forgotPasswordState.value = ForgotPasswordState.Loading

        viewModelScope.launch {
            try {
                Log.d("ForgotPasswordViewModel", "Отправка запроса восстановления для: $email")

                val response = RetrofitInstance.userManagementService.recoverPassword(
                    ForgotPasswordRequest(email = email)
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("ForgotPasswordViewModel", "Письмо отправлено успешно")
                        _showEmailSentDialog.value = true
                        _forgotPasswordState.value = ForgotPasswordState.Success // ИСПРАВЛЕНО!
                    } ?: run {
                        // Supabase может возвращать 200 с пустым телом
                        _showEmailSentDialog.value = true
                        _forgotPasswordState.value = ForgotPasswordState.Success // ИСПРАВЛЕНО!
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = parseForgotPasswordError(errorCode, errorBody)

                    Log.e("ForgotPasswordViewModel", "Ошибка восстановления: $errorCode - $errorMessage")
                    _forgotPasswordState.value = ForgotPasswordState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is ConnectException -> "Отсутствует соединение с интернетом"
                    is SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                Log.e("ForgotPasswordViewModel", "Ошибка: $errorMessage", e)
                _forgotPasswordState.value = ForgotPasswordState.Error(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Парсинг ошибок восстановления
    private fun parseForgotPasswordError(code: Int, errorBody: String): String {
        return when (code) {
            400 -> "Некорректный email"
            404 -> "Пользователь с таким email не найден"
            429 -> "Слишком много запросов. Попробуйте позже"
            500, 502, 503 -> "Ошибка сервера. Попробуйте позже"
            else -> when {
                errorBody.contains("user not found", ignoreCase = true) ->
                    "Пользователь с таким email не найден"
                errorBody.contains("rate limit", ignoreCase = true) ->
                    "Слишком много запросов. Попробуйте позже"
                else -> "Ошибка восстановления пароля"
            }
        }
    }

    // Закрыть диалог
    fun closeEmailSentDialog() {
        _showEmailSentDialog.value = false
    }

    // Сброс состояния
    fun resetState() {
        _forgotPasswordState.value = ForgotPasswordState.Idle
        _showEmailSentDialog.value = false
        _isLoading.value = false
    }

    // Очистка полей
    fun clearFields() {
        email = ""
        emailError = null
    }
}

sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    object Success : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}