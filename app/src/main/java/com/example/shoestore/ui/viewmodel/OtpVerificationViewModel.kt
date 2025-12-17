package com.example.shoestore.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoestore.data.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import com.example.shoestore.data.model.VerifyOtpRequest
class OtpVerificationViewModel : ViewModel() {
    // Данные OTP
    var otpCode by mutableStateOf("")
    var email by mutableStateOf("") // Email для верификации

    // Состояния таймера
    private var timerJob: Job? = null
    private val _timerSeconds = MutableStateFlow(60)
    val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _hasStartedTyping = MutableStateFlow(false)
    val hasStartedTyping: StateFlow<Boolean> = _hasStartedTyping.asStateFlow()

    // Ошибки и состояния
    var otpError by mutableStateOf<String?>(null)
    var showOtpError by mutableStateOf(false) // Флаг для показа ошибки OTP

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _otpState = MutableStateFlow<OtpState>(OtpState.Idle)
    val otpState: StateFlow<OtpState> = _otpState.asStateFlow()

    // Запуск таймера (01:00)
    fun startTimer() {
        if (_isTimerRunning.value) return

        _timerSeconds.value = 60
        _hasStartedTyping.value = true
        _isTimerRunning.value = true

        timerJob = viewModelScope.launch {
            while (_timerSeconds.value > 0 && _isTimerRunning.value) {
                delay(1000L)
                _timerSeconds.value--
            }
            if (_timerSeconds.value == 0) {
                _isTimerRunning.value = false
            }
        }
    }

    // Сброс таймера
    fun resetTimer() {
        timerJob?.cancel()
        _timerSeconds.value = 60
        _isTimerRunning.value = true
        startTimer()
    }

    // Форматирование времени
    fun formatTimer(): String {
        val minutes = _timerSeconds.value / 60
        val seconds = _timerSeconds.value % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // Обновление OTP кода
    fun updateOtpCode(code: String) {
        otpCode = code
        showOtpError = false

        if (!_hasStartedTyping.value && code.isNotEmpty()) {
            _hasStartedTyping.value = true
            startTimer()
        }

        // Автоматическая отправка при вводе 6 цифр
        if (code.length == 6) {
            verifyOtp()
        }
    }

    // Верификация OTP
    fun verifyOtp() {
        if (otpCode.length != 6) {
            otpError = "Введите 6-значный код"
            showOtpError = true
            _otpState.value = OtpState.Error("Введите 6-значный код")
            return
        }

        _isLoading.value = true
        _otpState.value = OtpState.Loading
        showOtpError = false

        viewModelScope.launch {
            try {
                Log.d("OtpVerificationViewModel", "Верификация OTP для: $email, код: $otpCode")

                val response = RetrofitInstance.userManagementService.verifyOtp(
                    VerifyOtpRequest(
                        email = email,
                        token = otpCode,
                        type = "email"
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("OtpVerificationViewModel", "OTP верификация успешна")
                        _otpState.value = OtpState.Success
                    } ?: run {
                        _otpState.value = OtpState.Error("Пустой ответ от сервера")
                        showOtpError = true
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: ""
                    val errorMessage = parseOtpError(errorCode, errorBody)

                    Log.e("OtpVerificationViewModel", "Ошибка верификации: $errorCode - $errorMessage")
                    _otpState.value = OtpState.Error(errorMessage)
                    showOtpError = true
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is ConnectException -> "Отсутствует соединение с интернетом"
                    is SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                Log.e("OtpVerificationViewModel", "Ошибка: $errorMessage", e)
                _otpState.value = OtpState.Error(errorMessage)
                showOtpError = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Парсинг ошибок OTP
    private fun parseOtpError(code: Int, errorBody: String): String {
        return when (code) {
            400 -> "Неверный OTP код"
            401 -> "OTP истек или недействителен"
            404 -> "Email не найден"
            429 -> "Слишком много попыток. Попробуйте позже"
            500, 502, 503 -> "Ошибка сервера. Попробуйте позже"
            else -> "Ошибка верификации: $errorBody"
        }
    }

    // Повторная отправка OTP
    fun resendOtp() {
        if (!_isTimerRunning.value && _timerSeconds.value == 0) {
            resetTimer()
            // TODO: Запрос на повторную отправку OTP
            Log.d("OtpVerificationViewModel", "Запрос на повторную отправку OTP")
        }
    }

    // Установка email
    fun setEmailForVerification(email: String) {
        this.email = email
    }

    // Сброс состояния
    fun resetState() {
        otpCode = ""
        otpError = null
        showOtpError = false
        _otpState.value = OtpState.Idle
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

sealed class OtpState {
    object Idle : OtpState()
    object Loading : OtpState()
    object Success : OtpState()
    data class Error(val message: String) : OtpState()
}