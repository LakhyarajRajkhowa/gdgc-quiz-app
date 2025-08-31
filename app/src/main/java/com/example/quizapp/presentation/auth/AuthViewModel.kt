package com.example.quizapp.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.storage.TokenManager
import com.example.quizapp.data.models.*
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: QuizRepository,
    private val tokenManager: TokenManager // ✅ inject TokenManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(scholarId: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val response = repository.login(LoginRequest(scholarId, password))

                // ✅ Instead of response.token (null), use stored token
                val token = tokenManager.getToken()
                if (token != null) {
                    _authState.value = AuthState.Success(token)
                    Log.d("LoginTest", "Login success: token=$token, user=$response")
                } else {
                    _authState.value = AuthState.Error("Token not found after login")
                    Log.d("LoginTest", "Login failed: token was null")
                }

            } catch (e: Exception) {
                _authState.value = AuthState.Error("Login failed: ${e.message}")
                Log.d("LoginTest", "Login failed: ${e.message}")
            }
        }
    }

    fun register(username: String, scholarId: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(User(username, scholarId, password))
                // Don’t force unwrap !! because message may be null
                val msg = response.message ?: "Registered successfully"
                _authState.value = AuthState.Message(msg)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }

    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val error: String) : AuthState()
    data class Message(val message: String) : AuthState()
}
