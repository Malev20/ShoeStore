package com.example.shoestore.ui.viewmodel
import com.example.shoestore.data.model.SignInRequest
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

class SignInViewModel : ViewModel() {
    // Данные формы
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)

    // Ошибки валидации
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    // Состояния запросов
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

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

    // Валидация пароля
    fun validatePassword(): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Пароль не может быть пустым"
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
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        return isEmailValid && isPasswordValid
    }

    // Вход пользователя
    fun signIn() {
        if (!validateForm()) {
            _signInState.value = SignInState.Error("Пожалуйста, заполните все поля корректно")
            return
        }

        _isLoading.value = true
        _signInState.value = SignInState.Loading

        viewModelScope.launch {
            try {
                Log.d("SignInViewModel", "Отправка запроса входа для: $email")

                val response = RetrofitInstance.userManagementService.signIn(
                    SignInRequest(
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let { signInResponse ->
                        // Сохраняем токен
                        saveAuthToken(signInResponse.access_token)
                        saveRefreshToken(signInResponse.refresh_token)

                        Log.d("SignInViewModel", "Вход успешен: ${signInResponse.user.email}")
                        _signInState.value = SignInState.Success
                    } ?: run {
                        _signInState.value = SignInState.Error("Пустой ответ от сервера")
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = parseSignInError(errorCode, errorBody)

                    Log.e("SignInViewModel", "Ошибка входа: $errorCode - $errorMessage")
                    _signInState.value = SignInState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is ConnectException -> "Отсутствует соединение с интернетом"
                    is SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                Log.e("SignInViewModel", "Ошибка: $errorMessage", e)
                _signInState.value = SignInState.Error(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Парсинг ошибок входа
    private fun parseSignInError(code: Int, errorBody: String): String {
        return when (code) {
            400 -> "Некорректный email или пароль"
            401 -> "Неверные учетные данные"
            422 -> "Неверный формат email"
            429 -> "Слишком много попыток входа. Попробуйте позже"
            500, 502, 503 -> "Ошибка сервера. Попробуйте позже"
            else -> "Ошибка входа: $errorBody"
        }
    }

    // Сохранение токена
    private fun saveAuthToken(token: String) {
        // TODO: Сохранить в SharedPreferences или SecureStorage
        Log.d("SignInViewModel", "Токен сохранен: ${token.take(10)}...")
    }

    private fun saveRefreshToken(token: String) {
        // TODO: Сохранить в SharedPreferences или SecureStorage
        Log.d("SignInViewModel", "Refresh токен сохранен: ${token.take(10)}...")
    }

    // Сброс состояния
    fun resetState() {
        _signInState.value = SignInState.Idle
        _isLoading.value = false
    }

    // Очистка полей
    fun clearFields() {
        email = ""
        password = ""
        passwordVisible = false
        emailError = null
        passwordError = null
    }
}

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}